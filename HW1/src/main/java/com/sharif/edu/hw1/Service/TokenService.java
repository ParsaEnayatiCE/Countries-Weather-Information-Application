package com.sharif.edu.hw1.Service;

import com.sharif.edu.hw1.DataBase.Token;
import com.sharif.edu.hw1.DataBase.TokenRepository;
import com.sharif.edu.hw1.Model.Security.TokenDto;
import com.sharif.edu.hw1.Model.Security.TokenRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private TokenRepository tokenRepository;

    public TokenDto createToken(TokenRequestDto tokenRequest) {
        // Here you can generate the token (e.g., JWT, random string, UUID)
        String generatedToken = generateToken();

        // Create a Token entity and save it to the database
        Token token = new Token();
        token.setName(tokenRequest.getName());
        token.setExpireDate(tokenRequest.getExpireDate());
        token.setToken(generatedToken);
        tokenRepository.save(token);

        // Return the created token DTO
        return new TokenDto(token.getName(), token.getExpireDate(), generatedToken);
    }

    public List<TokenDto> listTokens() {
        List<Token> tokens = tokenRepository.findAll();

        // Map Token entities to TokenDto objects
        return tokens.stream()
                .map(token -> new TokenDto(token.getName(), token.getExpireDate(), "***"))
                .collect(Collectors.toList());
    }

    public void revokeToken(String tokenId) {
        // Delete the token from the database based on its ID
        tokenRepository.deleteById(Long.parseLong(tokenId));
    }

    // Implement your token generation logic here
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}