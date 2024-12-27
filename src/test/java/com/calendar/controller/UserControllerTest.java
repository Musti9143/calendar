package com.calendar.controller;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.UserResponse;
import com.calendar.entities.User;
import com.calendar.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRequest userRequest;

    @InjectMocks
    private UserController userController_subjectUnderTest;

    @Test
    void createUser_shouldReturnOk_whenUserIsCreated(){

        when(userRequest.isValid()).thenReturn(true);
        when(userService.create(userRequest)).thenReturn(true);

        ResponseEntity<String> responseEntity = userController_subjectUnderTest.createUser(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully created!", responseEntity.getBody());
        verify(userService, times(1)).create(userRequest);
    }

    @Test
    void createUser_shouldReturnBadRequest_whenRequestIsInvalid(){

        when(userRequest.isValid()).thenReturn(false);

        ResponseEntity<String> responseEntity = userController_subjectUnderTest.createUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Something missing!", responseEntity.getBody());
        verify(userService, never()).create(userRequest);
    }

    @Test
    void createUser_shouldReturnOk_whenUserAlreadyExists(){

        when(userRequest.isValid()).thenReturn(true);
        when(userService.create(userRequest)).thenReturn(false);

        ResponseEntity<String> responseEntity = userController_subjectUnderTest.createUser(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User already exists!", responseEntity.getBody());

        verify(userService, times(1)).create(userRequest);
    }

    @Test
    void findUser_shouldReturnUserResponse_whenUserResponseIsNotNull(){

        final UserResponse userResponse = new UserResponse("Max", "Power", "max.power@email.com");

        when(userService.findUser("max.power@email.com")).thenReturn(userResponse);

        UserResponse result = userService.findUser("max.power@email.com");

        ResponseEntity<?> responseEntity = userController_subjectUnderTest.findUser("max.power@email.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(result, responseEntity.getBody());
    }

    @Test
    void findUser_shouldReturnNotFound_whenUserResponseIsNull(){

        when(userService.findUser("max.power@email.com")).thenReturn(null);

        ResponseEntity<?> responseEntity = userController_subjectUnderTest.findUser("max.power@email.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User could not be found!", responseEntity.getBody());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExistInRepo(){

        when(userService.delete("max.power@email.com")).thenReturn(true);
        ResponseEntity<String> responseEntity = userController_subjectUnderTest.deleteUser("max.power@email.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Successfully deleted {max.power@email.com}", responseEntity.getBody());
        verify(userService, times(1)).delete("max.power@email.com");
    }

    @Test
    void deleteUser_shouldNotDeleteUser_whenUserDoesNotExistInRepo(){

        when(userService.delete("max.power@email.com")).thenReturn(false);
        ResponseEntity<String> responseEntity = userController_subjectUnderTest.deleteUser("max.power@email.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User with given Email does not exist!", responseEntity.getBody());
        verify(userService, times(1)).delete("max.power@email.com");
    }
}
