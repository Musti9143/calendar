package com.calendar.calendar.controller;

import com.calendar.calendar.communication.in.UserRequestV1;
import com.calendar.calendar.dto.UserDTO;
import com.calendar.calendar.mapper.UserMapper;
import com.calendar.calendar.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequestV1 request) {
        if (!request.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something missing!");

        UserDTO userDTO = userMapper.toUserDto(request);

        if (userService.create(userDTO))
            return ResponseEntity.ok("Successfully created!");
        return ResponseEntity.ok("User already exists!");
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<?> findUser(@PathVariable String email){
        UserDTO userDTO = userService.findUser(email);
        if(userDTO != null)
            return ResponseEntity.ok(userDTO);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be found!");
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        if (userService.delete(email))
            return ResponseEntity.ok("Successfully deleted {" + email + "}");
        return ResponseEntity.ok("User with given Email does not exist!");
    }
}
