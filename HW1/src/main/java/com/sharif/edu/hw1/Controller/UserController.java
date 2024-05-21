package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Error;
import com.sharif.edu.hw1.Model.Security.UserRegistrationDto;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
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
        Error error = userService.register(registrationDto);
        return error.equals(Error.USER_NAME_ALREADY_EXIST) ? ResponseEntity.status(409).body(error.getErrorDesc()) : ResponseEntity.ok(error.getErrorDesc());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegistrationDto registrationDto) {
        String res = userService.login(registrationDto);
        if (!res.split(";")[0].equals("error")){
            ResponseCookie springCookie = ResponseCookie.from("token", res)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
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


