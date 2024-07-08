package com.sharif.edu.hw1.Service;

import com.sharif.edu.hw1.DataBase.TokenCookie;
import com.sharif.edu.hw1.DataBase.TokenCookieRepository;
import com.sharif.edu.hw1.DataBase.User;
import com.sharif.edu.hw1.DataBase.UserRepository;
import com.sharif.edu.hw1.Model.Error;
import com.sharif.edu.hw1.Model.Security.UserRegistrationDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sharif.edu.hw1.Model.Error.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenCookieRepository tokenCookieRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Error register(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()) != null){
            return USER_NAME_ALREADY_EXIST;
        }
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setActive(false);
        userRepository.save(newUser);
        return USER_REGISTERED_SUCCESSFULLY;
    }

    @Transactional
    public void registerAdmin() {
        if (userRepository.findByUsername("admin") != null){
            return;
        }
        User newUser = new User();
        newUser.setUsername("admin");
        newUser.setPassword(passwordEncoder.encode("admin"));
        newUser.setActive(true);
        userRepository.save(newUser);
    }

    @Transactional
    public String login(UserRegistrationDto registrationDto) {
        String username = registrationDto.getUsername();
        String password = registrationDto.getPassword();
        if (userRepository.findByUsername(username) == null) {
            return "error;" + Error.USER_NAME_NOT_FOUND.getErrorDesc();
        } else if (!passwordEncoder.matches(password, userRepository.findByUsername(username).getPassword())) {
            return "error;" + Error.PASSWORD_INCORRECT.getErrorDesc();
        }
        User newUser = userRepository.findByUsername(username);
        newUser.setLastLoginDate(System.currentTimeMillis());
        String token = TokenService.generateToken();
        userRepository.save(newUser);
        return token;
    }

    @Transactional
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public Error activateUser(String username, boolean isActive) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setActive(isActive);
            userRepository.save(user);
            return USER_ACTIVATED_SUCCESSFULLY;
        }
        return USER_NAME_NOT_FOUND;
    }

    @Transactional
    public boolean saveToken(String username, String token) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            long userID = user.getId();
            TokenCookie tokenCookie = new TokenCookie();
            tokenCookie.setToken(token);
            tokenCookie.setId(userID);
            tokenCookieRepository.save(tokenCookie);
            return true;
        }
        return false;
    }

    @Transactional
    public String getUserIdFromToken(String token) {
        Optional<TokenCookie> tokenCookieOptional = tokenCookieRepository.findByToken(token);
        if (tokenCookieOptional.isPresent()) {
            return String.valueOf(tokenCookieOptional.get().getUserId());
        }
        return "Expired";
    }
}