package com.sharif.edu.hw1.Service;

import com.sharif.edu.hw1.DataBase.User;
import com.sharif.edu.hw1.DataBase.UserRepository;
import com.sharif.edu.hw1.Model.Security.UserRegistrationDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserRegistrationDto registrationDto) {
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setActive(false);
        userRepository.save(newUser);
    }

    @Transactional
    public boolean activateUser(String username, boolean isActive) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setActive(isActive);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}