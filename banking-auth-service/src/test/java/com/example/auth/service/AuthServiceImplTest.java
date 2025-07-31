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
import com.example.auth.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("newuser");
        request.setPassword("password");
        request.setAccountHolderName("Hema");
        request.setAccountNumber("1234567890");
        request.setIfscCode("SBIN0011223");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");
        savedUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Map<String, String> response = authService.register(request);

        assertNotNull(response);
        assertEquals("User registered successfully", response.get("message"));
        verify(userRepository).save(any(User.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("existingUser");
        loginRequest.setPassword("password");

        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("encodedPassword");

        Account account = new Account();
        account.setAccountNumber("9876543210");
        user.setAccount(account);

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("existingUser")).thenReturn("mocked-jwt-token");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("Login successful", response.getMessage());
        assertEquals("9876543210", response.getAccountNumber());
    }

    @Test
    void testLoginInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("wrongpass");

        User user = new User();
        user.setUsername("user");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encodedPassword")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(loginRequest));
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("missing");
        loginRequest.setPassword("pass");

        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest));
    }
}
