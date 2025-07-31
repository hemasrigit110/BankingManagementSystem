package com.example.auth.security;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    void testGenerateTokenAndExtractUsername() throws Exception {
        JwtUtil jwtUtil = new JwtUtil();

        // Set a 256-bit secret key (32+ characters)
        String strongSecret = "my-super-secure-secret-key-123456";

        // Inject the secret using reflection
        Field secretField = JwtUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtUtil, strongSecret);

        // Generate and validate token
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
        assertTrue(token.startsWith("ey"));

        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }
}
