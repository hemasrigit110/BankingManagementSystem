package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setup() {
        account = new Account();
        account.setAccountNumber("ACC001");
        account.setBalance(new BigDecimal("1000.00"));
    }

    @Test
    void testGetAccountDetails_AccountExists() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(account));

        Account result = accountService.getAccountDetails("ACC001");

        assertNotNull(result);
        assertEquals("ACC001", result.getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), result.getBalance());
    }

    @Test
    void testGetAccountDetails_AccountNotFound() {
        when(accountRepository.findByAccountNumber("ACC999")).thenReturn(Optional.empty());

        Account result = accountService.getAccountDetails("ACC999");

        assertNull(result);
    }

    @Test
    void testUpdateBalance_CreditTransaction() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.updateBalance("ACC001", new BigDecimal("500.00"), "CREDIT");

        assertEquals(new BigDecimal("1500.00"), account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testUpdateBalance_DebitTransaction_Success() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.updateBalance("ACC001", new BigDecimal("400.00"), "DEBIT");

        assertEquals(new BigDecimal("600.00"), account.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void testUpdateBalance_DebitTransaction_InsufficientBalance() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateBalance("ACC001", new BigDecimal("2000.00"), "DEBIT");
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testUpdateBalance_InvalidTransactionType() {
        when(accountRepository.findByAccountNumber("ACC001")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateBalance("ACC001", new BigDecimal("100.00"), "TRANSFER");
        });

        assertEquals("Invalid transaction type", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void testUpdateBalance_AccountNotFound() {
        when(accountRepository.findByAccountNumber("ACC404")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateBalance("ACC404", new BigDecimal("100.00"), "CREDIT");
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, never()).save(any());
    }
}
