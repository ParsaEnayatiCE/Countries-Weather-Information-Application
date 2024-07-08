package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Security.TokenDto;
import com.sharif.edu.hw1.Model.Security.TokenRequestDto;
import com.sharif.edu.hw1.Model.TokenListResponseDto;
import com.sharif.edu.hw1.Service.TokenService;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user/api-tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> createToken(@RequestBody TokenRequestDto tokenRequest, @CookieValue(name = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.status(440).body("Session has been expired");
        }
        TokenDto t = tokenService.createToken(tokenRequest, Long.parseLong(userId));
        return ResponseEntity.ok(Map.of("token", t));
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> listTokens(@CookieValue(name = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.status(440).body("Session has been expired");
        }
        TokenListResponseDto tokens = tokenService.listTokens(Long.parseLong(userId));
        return ResponseEntity.ok(tokens);
    }

    @DeleteMapping("/{tokenName}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> revokeToken(@PathVariable String tokenName,@CookieValue(name = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.status(440).body("Session has been expired");
        }
        tokenService.revokeToken(tokenName, Long.parseLong(userId));
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}