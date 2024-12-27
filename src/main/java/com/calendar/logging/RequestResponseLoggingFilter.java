package com.calendar.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.UUID;

@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final long startTime = System.currentTimeMillis();
        final RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        final ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
        final String requestId = UUID.randomUUID().toString();

        try {
            logRequest(requestWrapper, requestId);
            chain.doFilter(requestWrapper, responseWrapper);
            final long duration = System.currentTimeMillis() - startTime;
            logResponse(responseWrapper, requestId, duration);
        } finally {
            destroy();
        }
    }

    @Override
    public void destroy() {
        // unused
    }

    private void logObject(@Nonnull final Object logObject) {
        try {
            final String logData = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(logObject);
            log.info("\n{}", logData);
        } catch (IOException e) {
            log.error("Error during logging: ", e);
        }
    }

    private Object parseJsonOrReturnString(@Nonnull final String rawBody) {
        try {
            return objectMapper.readValue(rawBody, Object.class);
        } catch (IOException e) {
            return rawBody;
        }
    }

    private void logRequest(@Nonnull final RequestWrapper requestWrapper, @Nonnull final String requestId) {
        try {
            final String rawRequestBody = new String(IOUtils.toByteArray(requestWrapper.getInputStream()));
            final Object formattedRequestBody = parseJsonOrReturnString(rawRequestBody);

            final RequestLogData requestLogData = new RequestLogData(
                    requestId,
                    requestWrapper.getMethod(),
                    requestWrapper.getRequestURI(),
                    formattedRequestBody
            );
            logObject(new RequestLog(requestLogData));
        } catch (IOException e) {
            log.error("Error during request logging: ", e);
        }
    }

    private void logResponse(@Nonnull final ResponseWrapper responseWrapper, @Nonnull final String requestId, final long duration) {
        final String rawResponseBody = responseWrapper.getResponseBody();
        final Object formattedResponseBody = parseJsonOrReturnString(rawResponseBody);

        final ResponseLogData responseLogData = new ResponseLogData(
                requestId,
                duration,
                responseWrapper.getStatus(),
                formattedResponseBody
        );
        logObject(new ResponseLog(responseLogData));
    }

    public static class RequestWrapper
            extends HttpServletRequestWrapper {

        final byte[] bufferedContent;

        public RequestWrapper(final HttpServletRequest request) throws IOException {
            super(request);
            bufferedContent = IOUtils.toByteArray(request.getInputStream());
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufferedContent);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(final ReadListener readListener) {
                    //unused
                }

                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }
    }

    public static class ResponseWrapper
            extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        private final OutputStream originalWriter;

        public ResponseWrapper(final HttpServletResponse response) throws IOException {
            super(response);
            originalWriter = response.getOutputStream();
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(originalWriter);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(final WriteListener writeListener) {
                    // unused
                }

                @Override
                public void write(@Nonnull final byte[] bytes, final int off, final int len) throws IOException {
                    originalWriter.write(bytes, off, len);
                    byteArrayOutputStream.write(bytes, off, len);
                }

                @Override
                public void write(final int b) throws IOException {
                    originalWriter.write(b);
                    byteArrayOutputStream.write(b);
                }
            };
        }

        public String getResponseBody() {
            return byteArrayOutputStream.toString();
        }
    }

    private record RequestLog(RequestLogData requestLogData) {
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private record RequestLogData(String requestId, String method, String requestUri, Object requestBody) {
    }

    private record ResponseLog(ResponseLogData responseLogData) {
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private record ResponseLogData(String responseToRequestId, @JsonIgnore long duration, Integer responseStatus,
                                   Object responseBody) {
        @JsonProperty("duration")
        public String getFormattedDuration() {
            if (duration < 1000) {
                return duration + "ms";
            } else {
                double seconds = duration / 1000.0;
                return seconds + "s";
            }
        }
    }
}
