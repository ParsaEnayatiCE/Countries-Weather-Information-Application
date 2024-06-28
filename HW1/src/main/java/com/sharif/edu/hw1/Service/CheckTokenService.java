package com.sharif.edu.hw1.Service;

import com.sharif.edu.hw1.DataBase.TokenCookie;
import com.sharif.edu.hw1.DataBase.TokenCookieRepository;
import com.sharif.edu.hw1.DataBase.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckTokenService {

    @Autowired
    private TokenCookieRepository tokenCookieRepository;

    public boolean checkAuthorization(String tokenCookie){
        Optional<TokenCookie> tokenCookieOptional = tokenCookieRepository.findByToken(tokenCookie);
        if(!tokenCookieOptional.isPresent()){
            return false;
        }
        return true;
    }

}
