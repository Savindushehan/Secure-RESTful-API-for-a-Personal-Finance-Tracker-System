package com.IT22354938.service;


import com.IT22354938.dto.UserRequest;
import com.IT22354938.models.User;
import com.IT22354938.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    JWTService jwtService;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
        public UserService(
                UserRepository userRepository,
                PasswordEncoder passwordEncoder,
                AuthenticationManager authManager,
                JWTService jwtService  // 👈 Add this
        ) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.authManager = authManager;
            this.jwtService = jwtService;  // 👈 Initialize
        }

    public User createUser(UserRequest userRequest) {
        User user = new User(
                userRequest.id(), // MongoDB will auto-generate the ID
                userRequest.name(),
                userRequest.nic(),
                userRequest.birthDay(),
                userRequest.userName(),
                passwordEncoder.encode(userRequest.password()), // Proper encoding here
                userRequest.role()
        );

        return userRepository.save(user);
    }

//    public Optional<User> getUserByUserNameAndPassword(String userName, String password) {
//        // Find user by username
//        Optional<User> userOptional = userRepository.findByUserName(userName);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            // Direct password comparison (not recommended for production)
//            if (user.getPassword().equals(password)) {
//                return Optional.of(user);  // Return user if password matches
//            }
//        }
//
//        return Optional.empty();  // Return empty if user not found or password is incorrect
//    }

    // Get All Users with ResponseEntity
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    // Get User by NIC
    public Optional<User> getUserByNIC(String nic) {
        return userRepository.findByNic(nic);
    }

    // Update User by NIC
    public User updateUserByNIC(String nic, UserRequest userRequest) {
        return userRepository.findByNic(nic).map(existingUser -> {
            existingUser.setName(userRequest.name());
            existingUser.setBirthDay(userRequest.birthDay());
            existingUser.setUserName(userRequest.userName());
            existingUser.setPassword(userRequest.password());
            existingUser.setRole(userRequest.role());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with NIC: " + nic));
    }

    // Delete User by NIC
    public void deleteUserByNIC(String nic) {
        Optional<User> user = userRepository.findByNic(nic);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new RuntimeException("User not found with NIC: " + nic);
        }
    }


    public ResponseEntity<?> verify(User user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUserName());
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
