package com.example.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
                                .csrf(csrf -> csrf.disable()) // Disable CSRF
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/account/**").permitAll() // Allow all requests to
                                                                                            // /account/**
                                                .anyRequest().authenticated()); // Authenticate all other requests
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*")); // Allow all origins
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow all common
                                                                                                     // methods
                configuration.setAllowedHeaders(List.of("*")); // Allow all headers
                configuration.setAllowCredentials(false); // Set to false when allowing all origins (*)

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration); // Apply CORS to all endpoints
                return source;
        }
}