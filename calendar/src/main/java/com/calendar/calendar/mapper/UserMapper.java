package com.calendar.calendar.mapper;

import com.calendar.calendar.communication.in.UserRequest;
import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDto(final UserRequest request) {
        return new UserDTO(request.name(), request.surname(), request.email());
    }

    public UserDTO toUserDto(final User user) {
        return new UserDTO(user.getName(), user.getSurname(), user.getEmail());
    }

    public User toUser(final UserDTO userDTO) {
        return new User(userDTO.name(), userDTO.surname(), userDTO.email());
    }
}
