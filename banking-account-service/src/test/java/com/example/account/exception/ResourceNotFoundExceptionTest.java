package com.example.account.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionFieldsAndMessage() {
        String resourceName = "Account";
        String fieldName = "accountNumber";
        String fieldValue = "ACC123";

        ResourceNotFoundException exception =
                new ResourceNotFoundException(resourceName, fieldName, fieldValue);

        // Assert getters
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(fieldValue, exception.getFieldValue());

        // Assert formatted message
        String expectedMessage = "Account not found with accountNumber : 'ACC123'";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
