package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Security.TokenDto;
import com.sharif.edu.hw1.Model.Security.TokenRequestDto;
import com.sharif.edu.hw1.Model.TokenListResponseDto;
import com.sharif.edu.hw1.Service.TokenService;
import com.sharif.edu.hw1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/api-tokens")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createToken(@RequestBody TokenRequestDto tokenRequest, @CookieValue(name = "token") String token) {
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.ok("Session has been expired");
        }
        TokenDto t = tokenService.createToken(tokenRequest, Long.parseLong(userId));
        return ResponseEntity.ok(Map.of("token", t));
    }

    @GetMapping
    public ResponseEntity<?> listTokens(@CookieValue(name = "token") String token) {
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.ok("Session has been expired");
        }
        TokenListResponseDto tokens = tokenService.listTokens(Long.parseLong(userId));
        return ResponseEntity.ok(tokens);
    }

    @DeleteMapping("/{tokenName}")
    public ResponseEntity<?> revokeToken(@PathVariable String tokenName,@CookieValue(name = "token") String token) {
        String userId = userService.getUserIdFromToken(token);
        if (userId.equals("Expired")){
            return ResponseEntity.ok("Session has been expired");
        }
        tokenService.revokeToken(tokenName, Long.parseLong(userId));
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}