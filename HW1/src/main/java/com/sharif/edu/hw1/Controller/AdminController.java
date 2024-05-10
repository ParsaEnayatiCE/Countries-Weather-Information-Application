package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PutMapping("/users")
    public ResponseEntity<?> activateUser(@RequestParam String username, @RequestParam boolean active) {
        boolean errorCode = userService.activateUser(username, active);
        return errorCode ? ResponseEntity.ok("User status updated.") : ResponseEntity.ok("Username not found or DB Problem");
    }
}