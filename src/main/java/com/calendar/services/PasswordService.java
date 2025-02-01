package com.calendar.services;

import com.calendar.config.SecurityConfig;
import com.calendar.entities.User;
import com.calendar.repositories.IUserRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final SecurityConfig securityConfig;
    private final IUserRepository userRepository;

    public PasswordService(@Nonnull final SecurityConfig securityConfig, @Nonnull final IUserRepository userRepository) {
        this.userRepository = userRepository;
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

    public boolean authenticateUser(@Nullable final String email, @Nullable final String password) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        return verifyPassword(password, user.getPassword());
    }
}
