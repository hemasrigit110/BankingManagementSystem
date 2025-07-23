//package com.example.auth.service;
//
//import com.example.auth.dto.AuthResponse;
//import com.example.auth.dto.LoginRequest;
//import com.example.auth.dto.SignUpRequest;
//import com.example.auth.exception.BadRequestException;
//import com.example.auth.exception.InvalidCredentialsException;
//import com.example.auth.exception.ResourceNotFoundException;
//import com.example.auth.model.Account;
//import com.example.auth.model.User;
//import com.example.auth.repository.AccountRepository;
//import com.example.auth.repository.UserRepository;
//import com.example.auth.security.JwtUtil;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AuthServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void login_returnsAuthResponse_whenCredentialsAreValid() {
//        // Arrange
//        User user = new User();
//        user.setUsername("validUser");
//        user.setPassword("encodedPassword");
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("validUser");
//        loginRequest.setPassword("validPassword");
//
//        when(userRepository.findByUsername("validUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("validPassword", "encodedPassword")).thenReturn(true);
//        when(jwtUtil.generateToken("validUser")).thenReturn("validToken");
//
//        // Act
//        AuthResponse response = authService.login(loginRequest);
//
//        // Assert
//        assertEquals("validToken", response.getToken());
//        assertEquals("Login successful", response.getMessage());
//    }
//
//    @Test
//    void login_throwsInvalidCredentialsException_whenPasswordIsIncorrect() {
//        // Arrange
//        User user = new User();
//        user.setUsername("validUser");
//        user.setPassword("encodedPassword");
//
//        when(userRepository.findByUsername("validUser")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("invalidPassword", "encodedPassword")).thenReturn(false);
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("validUser");
//        loginRequest.setPassword("invalidPassword");
//
//        // Act & Assert
//        assertThrows(InvalidCredentialsException.class,
//                () -> authService.login(loginRequest));
//    }
//
//    @Test
//    void login_throwsResourceNotFoundException_whenUserDoesNotExist() {
//        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("nonexistentUser");
//        loginRequest.setPassword("anyPassword");
//
//        assertThrows(ResourceNotFoundException.class,
//                () -> authService.login(loginRequest));
//    }
//
//    @Test
//    void register_returnsSuccessMessage_whenUserIsRegisteredSuccessfully() {
//        // Arrange
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("newUser");
//        request.setPassword("newPassword");
//        request.setAccountNumber("ACC123");
//        request.setAccountHolderName("Hemasri");
//        request.setIfscCode("IFSC001");
//
//        when(userRepository.existsByUsername("newUser")).thenReturn(false);
//        when(userRepository.existsByAccount_AccountNumber("ACC123")).thenReturn(false);
//        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(new User());
//
//        // Act
//        String result = authService.register(request);
//
//        // Assert
//        assertEquals("User registered successfully!", result);
//    }
//
//    @Test
//    void register_throwsBadRequestException_whenUsernameIsAlreadyTaken() {
//        when(userRepository.existsByUsername("existingUser")).thenReturn(true);
//
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("existingUser");
//
//        assertThrows(BadRequestException.class,
//                () -> authService.register(request));
//    }
//
//    @Test
//    void register_throwsBadRequestException_whenAccountNumberAlreadyExists() {
//        when(userRepository.existsByUsername("newUser")).thenReturn(false);
//        when(userRepository.existsByAccount_AccountNumber("ACC999")).thenReturn(true);
//
//        SignUpRequest request = new SignUpRequest();
//        request.setUsername("newUser");
//        request.setAccountNumber("ACC999");
//
//        assertThrows(BadRequestException.class,
//                () -> authService.register(request));
//    }
//}
