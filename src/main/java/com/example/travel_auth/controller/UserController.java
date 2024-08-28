package com.example.travel_auth.controller;

import com.example.travel_auth.Repository.UserRepository;
import com.example.travel_auth.checkuser.CheckUserRequest;
import com.example.travel_auth.checkuser.CheckUserResponse;
import com.example.travel_auth.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public String signUp(@RequestBody UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already taken";
        }
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/check-user")
    public CheckUserResponse checkUserExists(@RequestBody CheckUserRequest request) {
        boolean usernameExists = userRepository.findByUsername(request.getUsername()) != null;
        boolean emailExists = userRepository.findByEmail(request.getEmail()) != null;

        CheckUserResponse response = new CheckUserResponse();
        response.setUsernameExists(usernameExists);
        response.setEmailExists(emailExists);
        return response;
    }
}
