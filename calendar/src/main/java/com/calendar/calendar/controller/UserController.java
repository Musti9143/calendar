package com.calendar.calendar.controller;

import com.calendar.calendar.entities.User;
import com.calendar.calendar.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userService.create(user))
            return ResponseEntity.ok("Successfully created!");
        return ResponseEntity.ok("User already exists!");
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<?> findUser(@PathVariable String email){
        User user = userService.findUser(email);
        if(user != null)
            return ResponseEntity.ok(user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be found!");
    }

}
