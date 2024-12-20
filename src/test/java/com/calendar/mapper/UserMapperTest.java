package com.calendar.mapper;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setup() {
        userMapper = new UserMapper();
    }

    @Test
    void toUser_shouldMapUserRequestToUser() {
        final UserRequest userRequest = new UserRequest("Max", "Mustermann", "Max.Mustermann@email.com");
        final User user = userMapper.toUser(userRequest);
        assertEquals(userRequest.name(), user.getName());
        assertEquals(userRequest.surname(), user.getSurname());
        assertEquals(userRequest.email(), user.getEmail());
    }

    @Test
    void toUserResponse_shouldMapUserToUserResponse() {
        final User user = new User("Max", "Mustermann", "Max.Mustermann@email.com");
        final UserResponse userResponse = userMapper.toUserResponse(user);
        assertEquals(user.getName(), userResponse.name());
        assertEquals(user.getSurname(), userResponse.surname());
        assertEquals(user.getEmail(), userResponse.email());
    }
}
