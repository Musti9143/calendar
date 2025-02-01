package com.calendar.config;

import com.calendar.logging.AuthFilter;
import com.calendar.logging.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {

        final FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestResponseLoggingFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(Integer.MAX_VALUE - 1);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authenticationFilter(final AuthFilter authFilter) {

        final FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/v1/user/update/*");
        registrationBean.setOrder(Integer.MAX_VALUE);

        return registrationBean;
    }

}
