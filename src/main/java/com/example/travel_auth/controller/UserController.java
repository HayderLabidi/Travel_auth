package com.example.travel_auth.controller;

import com.example.travel_auth.Repository.UserRepository;
import com.example.travel_auth.checkuser.CheckUserRequest;
import com.example.travel_auth.checkuser.CheckUserResponse;
import com.example.travel_auth.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String usernameOrEmail = loginRequest.get("usernameOrEmail");
        String password = loginRequest.get("password");

        UserEntity user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        if (user != null && user.getPassword().equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("token", "dummy_token");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUserDetails(@RequestHeader("Authorization") String token) {
        String username = "test_user";  // For demonstration

        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
