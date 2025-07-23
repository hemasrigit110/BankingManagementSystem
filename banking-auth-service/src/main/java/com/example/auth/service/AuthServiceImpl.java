package com.example.auth.service;

import com.example.auth.dto.AuthResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.SignUpRequest;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.model.Account;
import com.example.auth.model.User;
import com.example.auth.repository.AccountRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.exception.BadRequestException;
import com.example.auth.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Login Function
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername())
                // .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", req.getUsername()));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        Account account = user.getAccount();

        System.out.println(account.getAccountNumber());

        String accountNumber = account.getAccountNumber();
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, "Login successful", accountNumber);
    }

    // Register Function
    // @Transactional
    // public String register(SignUpRequest request) {
    // if (userRepository.existsByUsername(request.getUsername())) {
    // throw new BadRequestException("Username is already taken!");
    // }
    //
    // User user = new User();
    // user.setUsername(request.getUsername());
    // user.setPassword(passwordEncoder.encode(request.getPassword()));
    // userRepository.save(user);
    //
    // Account account = new Account();
    // account.setAccountHolderName(request.getAccountHolderName());
    // account.setAccountNumber(request.getAccountNumber());
    // account.setIfscCode(request.getIfscCode());
    // //account.setBalance(BigDecimal.valueOf(0)); // Default initial balance
    // account.setUser(user);
    // accountRepository.save(account); // Save account after linking
    // return "User registered successfully";
    // }

    // Register Function
    @Transactional
    @Override
    public Map<String, String> register(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        Long userId = user.getId();

        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setAccountNumber(request.getAccountNumber());
        account.setIfscCode(request.getIfscCode());
        account.setUser(user);
        // account.setUserId(userId);
        accountRepository.save(account);

        return Collections.singletonMap("message", "User registered successfully");
    }

}
