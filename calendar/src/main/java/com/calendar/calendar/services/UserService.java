package com.calendar.calendar.services;

import com.calendar.calendar.entities.User;
import com.calendar.calendar.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public void create(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public User findUser(String email){
        return userRepository.findByEmail(email);
    }
}
