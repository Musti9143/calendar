package com.calendar.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String input) {

        return passwordEncoder.encode(input);
    }

    public boolean verifyPassword(String input, String hashedPassword) {

        return passwordEncoder.matches(input, hashedPassword);
    }
}
