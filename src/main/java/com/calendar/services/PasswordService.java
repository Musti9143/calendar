package com.calendar.services;

import com.calendar.config.SecurityConfig;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final SecurityConfig securityConfig;

    public PasswordService(@Nonnull final SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    private String concatPepperPassword(String password) {
        return password + securityConfig.getPepper();
    }

    public String hashPassword(String rawPassword) {

        return BCrypt.hashpw(concatPepperPassword(rawPassword), BCrypt.gensalt(12));
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {

        return BCrypt.checkpw(concatPepperPassword(rawPassword), hashedPassword);
    }
}
