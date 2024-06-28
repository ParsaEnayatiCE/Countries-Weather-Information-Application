package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Error;
import com.sharif.edu.hw1.Service.CheckTokenService;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private CheckTokenService checkTokenService;

    @PostMapping("/auth")
    public ResponseEntity<String> checkCookie(@RequestBody String tokenCookie) {
        boolean auth = checkTokenService.checkAuthorization(tokenCookie);
        return !auth ? ResponseEntity.status(401).body("Unauthorized") : ResponseEntity.status(200).body("Authorized");
    }
}
