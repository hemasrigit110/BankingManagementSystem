package com.example.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

class AccountTest {


    @Test
    void createAccount_returnsAccountWithCorrectDetails() {
        Account account = new Account();
        account.setAccountHolderName("John Doe");
        account.setAccountNumber("1234567890");
        account.setIfscCode("IFSC1234");
        //account.setBalance(new BigDecimal("1000.00"));

        assertEquals("John Doe", account.getAccountHolderName());
        assertEquals("1234567890", account.getAccountNumber());
        assertEquals("IFSC1234", account.getIfscCode());
       // assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    void setAccountDetails_updatesAccountDetailsCorrectly() {
        Account account = new Account();
        account.setAccountHolderName("Jane Doe");
        account.setAccountNumber("9876543210");
        account.setIfscCode("IFSC5678");
        //account.setBalance(new BigDecimal("2000.00"));

        assertEquals("Jane Doe", account.getAccountHolderName());
        assertEquals("9876543210", account.getAccountNumber());
        assertEquals("IFSC5678", account.getIfscCode());
       // assertEquals(new BigDecimal("2000.00"), account.getBalance());
    }

    @Test
    void getUser_returnsAssociatedUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Account account = new Account();
        account.setUser(user);

        assertNotNull(account.getUser());
        assertEquals(1L, account.getUser().getId());
        assertEquals("testuser", account.getUser().getUsername());
    }

    @Test
    void setUser_associatesUserWithAccount() {
        User user = new User();
        user.setId(2L);
        user.setUsername("anotheruser");

        Account account = new Account();
        account.setUser(user);

        assertEquals(2L, account.getUser().getId());
        assertEquals("anotheruser", account.getUser().getUsername());
    }
}