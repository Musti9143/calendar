package com.calendar.services;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import com.calendar.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(final IUserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean create(final UserRequest userRequest) {
        final User user = userMapper.toUser(userRequest);

        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(final String email) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    public void deleteById(final UUID id) {

        userRepository.deleteById(id);
    }

    public void update(final User user) {

        userRepository.save(user);
    }

    public UserResponse findUser(final String email) {

        final User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return userMapper.toUserResponse(user);
    }
}
