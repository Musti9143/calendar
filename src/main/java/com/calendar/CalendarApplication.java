package com.calendar;

import com.calendar.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SecurityConfig.class)
@SpringBootApplication
public class CalendarApplication {

    public static void main(String[] args) {

        SpringApplication.run(CalendarApplication.class, args);
    }
}
