package com.calendar.services;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import com.calendar.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void create_shouldReturnTrueAndVerifySave_whenUserDoesNotExist() {
        final UserRequest userRequest = new UserRequest("Max", "Power", "max.power@email.com");
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertTrue(userService.create(userRequest));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void create_shouldReturnFalse_whenUserAlreadyExists() {
        final UserRequest userRequest = new UserRequest("Max", "Power", "max.power@email.com");
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertFalse(userService.create(userRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_shouldReturnFalse_whenUserDoesNotExist() {
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertFalse(userService.delete(user.getEmail()));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void delete_shouldReturnTrueAndVerifyDelete_whenUserExist() {
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertTrue(userService.delete(user.getEmail()));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void findUser_shouldReturnNullWhenUserNotFound() {
        when(userRepository.findByEmail("max.power@email.com")).thenReturn(null);

        UserResponse result = userService.findUser("max.power@email.com");

        assertNull(result);

        verify(userRepository, times(1)).findByEmail("max.power@email.com");
        verify(userMapper, never()).toUserResponse(any());

    }

    @Test
    void findUser_shouldReturnUserResponseWhenUserIsFound() {
        final User user = new User("Max", "Power", "max.power@email.com");
        final UserResponse userResponse = new UserResponse("Max", "Power", "max.power@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.findUser("max.power@email.com");

        assertNotNull(result);
        assertEquals("Max", result.name());
        assertEquals("Power", result.surname());
        assertEquals("max.power@email.com", result.email());

        verify(userRepository, times(1)).findByEmail("max.power@email.com");
        verify(userMapper, times(1)).toUserResponse(user);
    }
}