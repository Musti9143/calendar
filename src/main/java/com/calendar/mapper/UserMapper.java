package com.calendar.mapper;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequest userRequest) {
        return new User(userRequest.name(), userRequest.surname(), userRequest.email());
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getName(), user.getSurname(), user.getEmail());
    }
}
