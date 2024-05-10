package com.sharif.edu.hw1.Controller;

import com.sharif.edu.hw1.Model.Security.TokenDto;
import com.sharif.edu.hw1.Model.Security.TokenRequestDto;
import com.sharif.edu.hw1.Service.TokenService;
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

    @PostMapping
    public ResponseEntity<?> createToken(@RequestBody TokenRequestDto tokenRequest) {
        TokenDto token = tokenService.createToken(tokenRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping
    public ResponseEntity<?> listTokens() {
        List<TokenDto> tokens = tokenService.listTokens();
        return ResponseEntity.ok(tokens);
    }

    @DeleteMapping("/{tokenId}")
    public ResponseEntity<?> revokeToken(@PathVariable String tokenId) {
        tokenService.revokeToken(tokenId);
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}