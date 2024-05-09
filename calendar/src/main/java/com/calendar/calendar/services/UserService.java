package com.calendar.calendar.services;

import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.User;
import com.calendar.calendar.mapper.UserMapper;
import com.calendar.calendar.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(IUserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Boolean create(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);

        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public UserDTO findUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        return userMapper.toUserDto(user);
    }
}
