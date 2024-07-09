package com.sharif.edu.hw1.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharif.edu.hw1.DataBase.User;
import com.sharif.edu.hw1.Model.Error;
import com.sharif.edu.hw1.Model.Security.UserRegistrationDto;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PutMapping("/users")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<?> activateUser(@RequestParam String username, @RequestParam boolean active, @CookieValue(name = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Error error= userService.activateUser(username, active);
        return error.equals(Error.USER_NAME_NOT_FOUND) ? ResponseEntity.status(400).body(error.getErrorDesc()) : ResponseEntity.ok(error.getErrorDesc());
    }

    @GetMapping("/users")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<?> activateUser(@CookieValue(name = "token", required = false) String token) throws JsonProcessingException {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<User> allUsers = userService.allUsers();
        return ResponseEntity.status(200).body(allUsers);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
    public ResponseEntity<?> login(@RequestBody UserRegistrationDto registrationDto) {
        userService.registerAdmin();
        String res = userService.login(registrationDto);
        if (!res.split(";")[0].equals("error")){
            ResponseCookie springCookie = ResponseCookie.from("token", res)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(3600)
                    .build();
            userService.saveToken(registrationDto.getUsername(), res);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, springCookie.toString());
            return ResponseEntity.ok().headers(headers)
                    .build();
        }
        return ResponseEntity.status(500).body(res.split(";")[1]);
    }
}