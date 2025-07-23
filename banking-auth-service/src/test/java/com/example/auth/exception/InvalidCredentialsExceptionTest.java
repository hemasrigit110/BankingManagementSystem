package com.example.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCredentialsExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Invalid username or password";
        InvalidCredentialsException exception = new InvalidCredentialsException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionIsInstanceOfRuntimeException() {
        InvalidCredentialsException exception = new InvalidCredentialsException("error");
        assertTrue(exception instanceof RuntimeException);
    }
}
