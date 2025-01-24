package com.calendar.controller;

import com.calendar.communication.in.UserRequest;
import com.calendar.communication.out.ErrorResponse;
import com.calendar.communication.out.GenericResponse;
import com.calendar.communication.out.UserResponse;
import com.calendar.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<String, ErrorResponse>> createUser(@RequestBody final UserRequest userRequest) {

        if (!userRequest.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(GenericResponse.error(new ErrorResponse("Something missing!")));

        if (userService.create(userRequest))
            return ResponseEntity.ok(GenericResponse.success("Successfully created!"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.error(new ErrorResponse("User already exists!")));
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<GenericResponse<UserResponse, ErrorResponse>> findUser(@PathVariable final String email) {

        final UserResponse userResponse = userService.findUser(email);
        if (userResponse != null)
            return ResponseEntity.ok(GenericResponse.success(userResponse));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(GenericResponse.error(new ErrorResponse("User could not be found!")));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<GenericResponse<String, ErrorResponse>> deleteUser(@PathVariable final String email) {

        if (userService.delete(email))
            return ResponseEntity.ok(GenericResponse.success("Successfully deleted {" + email + "}"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(GenericResponse.error(new ErrorResponse("User with given Email does not exist!")));
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse<UserResponse, ErrorResponse>> updateUser(@RequestBody final UserRequest userRequest) {

        if (!userRequest.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(GenericResponse.error(new ErrorResponse("Something missing!")));

        final UserResponse userResponse = userService.update(userRequest);
        if (userResponse != null)
            return ResponseEntity.ok(GenericResponse.success(userResponse));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(GenericResponse.error(new ErrorResponse("User could not be found!")));

    }

}
