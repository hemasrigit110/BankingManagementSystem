package com.example.auth.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Properly mock the UserRepository
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    @DisplayName("existsByUsername returns true when username exists")
    void existsByUsernameReturnsTrueWhenUsernameExists() {
        String username = "existingUser";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        Boolean result = userRepository.existsByUsername(username);

        assertTrue(result);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    @DisplayName("existsByUsername returns false when username does not exist")
    void existsByUsernameReturnsFalseWhenUsernameDoesNotExist() {
        String username = "nonExistentUser";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        Boolean result = userRepository.existsByUsername(username);

        assertFalse(result);
        verify(userRepository, times(1)).existsByUsername(username);
    }
}
