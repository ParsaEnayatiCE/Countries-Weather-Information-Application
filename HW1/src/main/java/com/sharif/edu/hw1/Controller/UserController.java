package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Security.UserRegistrationDto;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.register(registrationDto);
        return ResponseEntity.ok("User registered successfully, pending activation.");
    }
}


