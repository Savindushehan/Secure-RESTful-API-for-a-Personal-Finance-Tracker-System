package com.IT22354938.controller;


import com.IT22354938.dto.UserRequest;
import com.IT22354938.models.User;
import com.IT22354938.service.JWTService;
import com.IT22354938.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final String SECRET_KEY = "your_secret_key";  // Replace with a secure key
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
//        Optional<User> userOptional = userService.getUserByUserNameAndPassword(userRequest.userName(), userRequest.password());
//
//        if (userOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        User user = userOptional.get();
//
//        // Generate JWT Token
//        String token = Jwts.builder()
//                .setSubject(user.getUserName())
//                .claim("role", user.getRole())  // Include role in token payload
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1-day expiration
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//
//        // Return token with user details
//        Map<String, Object> response = new HashMap<>();
//        response.put("token", token);
//        response.put("user", user);
//
//        return ResponseEntity.ok(response);
//    }

    // Get User by NIC
    @GetMapping("/{nic}")
    public ResponseEntity<User> getUserByNIC(@PathVariable String nic) {
        Optional<User> user = userService.getUserByNIC(nic);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update User by NIC
    @PutMapping("/{nic}")
    public ResponseEntity<User> updateUserByNIC(@PathVariable String nic, @RequestBody UserRequest userRequest) {
        try {
            User updatedUser = userService.updateUserByNIC(nic, userRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete User by NIC
    @DeleteMapping("/{nic}")
    public ResponseEntity<Void> deleteUserByNIC(@PathVariable String nic) {
        try {
            userService.deleteUserByNIC(nic);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        return userService.verify(user);
    }



}

