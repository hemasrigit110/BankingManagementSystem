//package com.example.auth.controller;
//
//import com.example.auth.dto.*;
//import com.example.auth.exception.InvalidCredentialsException;
//import com.example.auth.model.Account;
//import com.example.auth.model.User;
//import com.example.auth.repository.UserRepository;
//import com.example.auth.security.JwtUtil;
//import com.example.auth.service.AuthServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.autoconfigure.mockito.MockBean;
//
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AuthController.class)
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AuthServiceImpl authService;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testLogin_Success() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("hemasri");
//        loginRequest.setPassword("password");
//
//        AuthResponse mockResponse = new AuthResponse();
//        mockResponse.setToken("dummy-jwt-token");
//
//        when(authService.login(any(LoginRequest.class))).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("dummy-jwt-token"));
//    }
//
//    @Test
//    void testLogin_InvalidCredentials() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("wronguser");
//        loginRequest.setPassword("wrongpass");
//
//        when(authService.login(any(LoginRequest.class)))
//                .thenThrow(new InvalidCredentialsException("Invalid username or password"));
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.success").value(false))
//                .andExpect(jsonPath("$.message").value("Authentication failed: Invalid username or password"));
//    }
//
//    @Test
//    void testRegister_Success() throws Exception {
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("hemasri");
//        request.setPassword("password123");
//        request.setAccountNumber("ACC123");
//        request.setIfscCode("IFSC001");
//        request.setAccountHolderName("Hemasri");
//
//        when(userRepository.existsByUsername("hemasri")).thenReturn(false);
//        when(userRepository.existsByAccount_AccountNumber("ACC123")).thenReturn(false);
//        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
//
//        when(userRepository.save(any(User.class))).thenReturn(new User());
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("User registered successfully!"));
//    }
//
//    @Test
//    void testRegister_UsernameExists() throws Exception {
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("hemasri");
//        request.setPassword("password123");
//        request.setAccountNumber("ACC123");
//        request.setIfscCode("IFSC001");
//        request.setAccountHolderName("Hemasri");
//
//        when(userRepository.existsByUsername("hemasri")).thenReturn(true);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Username is already taken!"));
//    }
//
//    @Test
//    void testRegister_AccountNumberExists() throws Exception {
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("hemasri");
//        request.setPassword("password123");
//        request.setAccountNumber("ACC123");
//        request.setIfscCode("IFSC001");
//        request.setAccountHolderName("Hemasri");
//
//        when(userRepository.existsByUsername("hemasri")).thenReturn(false);
//        when(userRepository.existsByAccount_AccountNumber("ACC123")).thenReturn(true);
//
//        mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Account number is already registered!"));
//    }
//}
