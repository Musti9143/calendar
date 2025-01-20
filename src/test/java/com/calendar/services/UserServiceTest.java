package com.calendar.services;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import com.calendar.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private UserRequest userRequest;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {

        userRequest = new UserRequest("Max", "Power", "max.power@email.com");
        user = new User("Max", "Power", "max.power@email.com");
        userResponse = new UserResponse("Max", "Power", "max.power@email.com");
    }

    @Test
    void create_shouldReturnTrue_whenUserDoesNotExist() {

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        boolean result = userService.create(userRequest);

        assertTrue(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void create_shouldReturnFalse_whenUserAlreadyExist() {

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertFalse(userService.create(userRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_shouldReturnFalse_whenUserDoesNotExist() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertFalse(userService.delete(user.getEmail()));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void delete_shouldReturnTrue_whenUserExist() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertTrue(userService.delete(user.getEmail()));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void update_shouldReturnNull_whenUserDoesNotExist() {

        when(userRepository.findByEmail(userRequest.email())).thenReturn(null);

        assertNull(userService.update(userRequest));

        verify(userRepository, times(1)).findByEmail(userRequest.email());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toUserResponse(any());
    }

    @Test
    void update_shouldReturnUserResponse_whenUserIsUpdated() {

        when(userRepository.findByEmail(userRequest.email())).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.update(userRequest);

        assertNotNull(result);
        assertEquals(userRequest.name(), result.name());
        assertEquals(userRequest.surname(), result.surname());

        verify(userRepository, times(1)).findByEmail(userRequest.email());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void findUser_shouldReturnNull_WhenUserNotFound() {

        when(userRepository.findByEmail("max.power@email.com")).thenReturn(null);

        UserResponse result = userService.findUser("max.power@email.com");

        assertNull(result);

        verify(userRepository, times(1)).findByEmail("max.power@email.com");
        verify(userMapper, never()).toUserResponse(any());

    }

    @Test
    void findUser_shouldReturnUserResponse_WhenUserIsFound() {

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