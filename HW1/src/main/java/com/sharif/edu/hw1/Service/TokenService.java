package com.sharif.edu.hw1.Service;

import com.sharif.edu.hw1.DataBase.Token;
import com.sharif.edu.hw1.DataBase.TokenRepository;
import com.sharif.edu.hw1.DataBase.UserRepository;
import com.sharif.edu.hw1.Model.Security.TokenDto;
import com.sharif.edu.hw1.Model.Security.TokenRequestDto;
import com.sharif.edu.hw1.Model.TokenListResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TokenDto createToken(TokenRequestDto tokenRequest, long userID) {
        // Here you can generate the token (e.g., JWT, random string, UUID)
        String generatedToken = generateToken();
        // Create a Token entity and save it to the database
        Token token = new Token();
        token.setName(tokenRequest.getName());
        token.setExpireDate(tokenRequest.getExpireDate());
        token.setToken(generatedToken);
        token.setUserId(userID);
        tokenRepository.save(token);

        // Return the created token DTO
        return new TokenDto(token.getName(), token.getExpireDate(), generatedToken);
    }

    @Transactional
    public TokenListResponseDto  listTokens(long userId) {
        List<Token> tokens = tokenRepository.findAllByUserId(userId);

        // Map Token entities to TokenDto objects
        List<TokenDto> tokenDtos = tokens.stream()
                .map(token -> new TokenDto(token.getName(), token.getExpireDate(), "***"))
                .collect(Collectors.toList());

        // Create TokenListResponseDto and set tokens list and count
        TokenListResponseDto responseDto = new TokenListResponseDto();
        responseDto.setTokens(tokenDtos);
        responseDto.setCount(tokenDtos.size());

        return responseDto;
    }

    @Transactional
    public void revokeToken(String tokenName, long userId) {
        tokenRepository.deleteByNameAndUserId(tokenName, userId);
    }

    // Implement your token generation logic here
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}