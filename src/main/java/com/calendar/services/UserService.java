package com.calendar.services;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import com.calendar.repositories.IUserRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;

    public UserService(@Nonnull final IUserRepository userRepository, @Nonnull final UserMapper userMapper,
                       @Nonnull final PasswordService passwordService) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    public boolean create(@Nonnull final UserRequest userRequest) {
        final User user = userMapper.toUser(userRequest);

        if (userRepository.findByEmail(user.getEmail()) == null) {

            String hashedPassword = passwordService.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(@Nonnull final String email) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    public boolean update(@Nonnull final UserRequest userRequest) {

        final User user = userRepository.findByEmail(userRequest.email());
        if (user == null)
            return false;

        user.setName(userRequest.name());
        user.setSurname(userRequest.surname());

        userRepository.save(user);

        return true;
    }

    public UserResponse findUser(@Nonnull final String email) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return userMapper.toUserResponse(user);
    }
}
