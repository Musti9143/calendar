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

    public UserService(@Nonnull final IUserRepository userRepository, @Nonnull final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean create(@Nonnull final UserRequest userRequest) {
        final User user = userMapper.toUser(userRequest);

        if (userRepository.findByEmail(user.getEmail()) == null) {
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

    public UserResponse update(@Nonnull final UserRequest userRequest) {

        final User user = userRepository.findByEmail(userRequest.email());
        if (user == null)
            return null;

        user.setName(userRequest.name());
        user.setSurname(userRequest.surname());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    public UserResponse findUser(@Nonnull final String email) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return userMapper.toUserResponse(user);
    }
}
