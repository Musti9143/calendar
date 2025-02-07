package com.calendar.controller;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.ErrorResponse;
import com.calendar.communication.out.GenericResponse;
import com.calendar.communication.out.UserResponse;
import com.calendar.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRequest userRequest;

    @InjectMocks
    private UserController userController;

    final UserResponse userResponse = new UserResponse("Max", "Power", "max.power@email.com");

    @Test
    void createUser_shouldReturnOk_whenUserIsCreated() {

        when(userRequest.isValid()).thenReturn(true);
        when(userService.create(userRequest)).thenReturn(true);

        ResponseEntity<GenericResponse<String>> responseEntity = userController.createUser(userRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success("Successfully created!"), responseEntity.getBody());
        verify(userService, times(1)).create(userRequest);
    }

    @Test
    void createUser_shouldReturnBadRequest_whenRequestIsInvalid() {

        when(userRequest.isValid()).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = userController.createUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Something missing!")),
                responseEntity.getBody());
        verify(userService, never()).create(userRequest);
    }

    @Test
    void createUser_shouldReturnBadRequest_whenUserAlreadyExists() {

        when(userRequest.isValid()).thenReturn(true);
        when(userService.create(userRequest)).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = userController.createUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("User already exists!")),
                responseEntity.getBody());
        verify(userService, times(1)).create(userRequest);
    }

    @Test
    void findUser_shouldReturnUserResponse_whenUserResponseIsNotNull() {

        when(userService.findUser("max.power@email.com")).thenReturn(userResponse);

        UserResponse result = userService.findUser("max.power@email.com");

        ResponseEntity<GenericResponse<UserResponse>> responseEntity = userController
                .findUser("max.power@email.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success(result), responseEntity.getBody());
    }

    @Test
    void findUser_shouldReturnNotFound_whenUserResponseIsNull() {

        when(userService.findUser("max.power@email.com")).thenReturn(null);

        ResponseEntity<GenericResponse<UserResponse>> responseEntity = userController
                .findUser("max.power@email.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("User could not be found!")),
                responseEntity.getBody());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExistInRepo() {

        when(userService.delete("max.power@email.com")).thenReturn(true);
        ResponseEntity<GenericResponse<String>> responseEntity = userController
                .deleteUser("max.power@email.com");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(GenericResponse.success("Successfully deleted {max.power@email.com}")
                , responseEntity.getBody());
        verify(userService, times(1)).delete("max.power@email.com");
    }

    @Test
    void deleteUser_shouldNotDeleteUser_whenUserDoesNotExistInRepo() {

        when(userService.delete("max.power@email.com")).thenReturn(false);
        ResponseEntity<GenericResponse<String>> responseEntity = userController
                .deleteUser("max.power@email.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("User with given Email does not exist!"))
                , responseEntity.getBody());
        verify(userService, times(1)).delete("max.power@email.com");
    }

    @Test
    void updateUser_shouldReturnBadRequest_whenUserIsInvalid() {

        when(userRequest.isValid()).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = userController
                .updateUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("Something missing!"))
                , responseEntity.getBody());
        verify(userService, never()).update(userRequest);
    }

    @Test
    void updateUser_shouldReturnOk_whenUserIsUpdated() {

        when(userRequest.isValid()).thenReturn(true);
        when(userService.update(userRequest)).thenReturn(true);

        ResponseEntity<GenericResponse<String>> responseEntity = userController
                .updateUser(userRequest);

        assertEquals(GenericResponse.success("Successfully updated!"), responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).update(userRequest);

    }

    @Test
    void updateUser_shouldReturnNotFound_whenUserDoesNotExist() {

        when(userRequest.isValid()).thenReturn(true);
        when(userService.update(userRequest)).thenReturn(false);

        ResponseEntity<GenericResponse<String>> responseEntity = userController
                .updateUser(userRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(GenericResponse.error(new ErrorResponse("User could not be found!"))
                , responseEntity.getBody());
        verify(userService, times(1)).update(userRequest);
    }
}
