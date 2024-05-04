package com.calendar.calendar.services;

import com.calendar.calendar.entities.User;
import com.calendar.calendar.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean create(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser == null)
            return userRepository.save(user) != null;
        return false;
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
