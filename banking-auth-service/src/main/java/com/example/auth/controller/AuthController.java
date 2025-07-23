package com.example.auth.controller;

import com.example.auth.dto.ApiResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.SignUpRequest;
import com.example.auth.exception.BadRequestException;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.model.Account;
import com.example.auth.model.User;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.AuthServiceImpl;
import jakarta.validation.Valid;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private com.example.auth.repository.UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        try {
            com.example.auth.dto.AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, "Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequest request) {

        System.out.println("HERE");
        System.out.println(request);
        System.out.println("HERE");

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Check if account number already exists
        if (userRepository.existsByAccount_AccountNumber(request.getAccountNumber())) {
            return ResponseEntity.badRequest().body("Account number is already registered!");
        }

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Create Account
        Account account = new Account();
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountHolderName(request.getAccountHolderName());
        account.setIfscCode(request.getIfscCode());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType("SAVINGS");

        // //  CRITICAL: Set bidirectional relationship
        account.setUser(user); // ← Owning side
        user.setAccount(account); // ← Inverse side

        try {
            userRepository.save(user); // cascades to Account
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {

            System.out.println("HERE IN ERROR");

            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

}


