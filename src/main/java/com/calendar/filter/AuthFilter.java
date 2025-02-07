package com.calendar.filter;

import com.calendar.services.PasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private final PasswordService passwordService;

    public AuthFilter(@Nonnull final PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final String headerEmail = httpServletRequest.getHeader("email");
        final String headerPassword = httpServletRequest.getHeader("password");

        if (StringUtils.isBlank(headerEmail) || StringUtils.isBlank(headerPassword)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        final boolean isAuthenticated = passwordService.authenticateUser(headerEmail, headerPassword);

        if (!isAuthenticated) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }

}