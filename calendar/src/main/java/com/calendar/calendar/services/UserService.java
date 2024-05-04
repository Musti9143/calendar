package com.calendar.calendar.services;

import com.calendar.calendar.entities.User;
import com.calendar.calendar.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean create(User user) {
        return userRepository.save(user) != null;
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
