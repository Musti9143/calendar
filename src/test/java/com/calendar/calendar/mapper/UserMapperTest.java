package com.calendar.calendar.mapper;

import com.calendar.communication.in.UserRequest;
import com.calendar.dto.UserDTO;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private UserMapper userMapper;
    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setup(){
        userMapper = new UserMapper();
    }

    @Test
    void toUserDto_shouldMapUserRequestToUserDTO(){
        UserRequest userRequest = new UserRequest("Max", "Mustermann", "Max.Mustermann@email.com");
        UserDTO userDTO = userMapper.toUserDto(userRequest);
        assertEquals(userRequest.name(),userDTO.name());
        assertEquals(userRequest.surname(),userDTO.surname());
        assertEquals(userRequest.email(),userDTO.email());
    }
    @Test
    void toUserDto_shouldMapUserToUserDTO(){
        User user = new User("Max", "Mustermann", "Max.Mustermann@email.com");
        UserDTO userDTO = userMapper.toUserDto(user);
        assertEquals(user.getName(),userDTO.name());
        assertEquals(user.getSurname(),userDTO.surname());
        assertEquals(user.getEmail(),userDTO.email());
    }
    @Test
    void toUser_shouldMapUserDTOToUser(){
        UserDTO userDTO = new UserDTO("Max", "Mustermann", "Max.Mustermann@email.com");
        User user = userMapper.toUser(userDTO);
        assertEquals(user.getName(),userDTO.name());
        assertEquals(user.getSurname(),userDTO.surname());
        assertEquals(user.getEmail(),userDTO.email());
    }
}
