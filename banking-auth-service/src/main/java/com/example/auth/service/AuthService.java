package com.example.auth.service;



import com.example.auth.dto.AuthResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.SignUpRequest;

import java.util.Map;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    Map<String, String> register(SignUpRequest request);
}

