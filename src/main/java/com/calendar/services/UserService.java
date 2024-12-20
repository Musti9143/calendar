package com.calendar.services;

import com.calendar.dto.UserDTO;
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

    public boolean create(final UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);

        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(final String email) {
        User user = userRepository.findByEmail(email);
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

    public UserDTO findUser(final String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return userMapper.toUserDto(user);
    }
}
