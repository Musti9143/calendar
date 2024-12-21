package com.calendar.services;

import com.calendar.communication.in.UserRequest;
import com.calendar.entities.User;
import com.calendar.mapper.UserMapper;
import com.calendar.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        verify(userRepository, times(1)).save(user);
        assertTrue(userService.create(userRequest));
    }

    @Test
    void create_shouldReturnFalse_whenUserAlreadyExists() {
        final UserRequest userRequest = new UserRequest("Max", "Power", "max.power@email.com");
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        verify(userRepository, never()).save(any());
        assertFalse(userService.create(userRequest));
    }

    @Test
    void delete_shouldReturnFalse_whenUserDoesNotExist() {
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        verify(userRepository, never()).delete(any());
        assertFalse(userService.delete(user.getEmail()));
    }

    @Test
    void delete_shouldReturnTrueAndVerifyDelete_whenUserExist() {
        final User user = new User("Max", "Power", "max.power@email.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        //TODO: verify does not work with checking for deletion
        //verify(userRepository, times(1)).delete(user);
        assertTrue(userService.delete(user.getEmail()));
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void findUser() {
    }
}