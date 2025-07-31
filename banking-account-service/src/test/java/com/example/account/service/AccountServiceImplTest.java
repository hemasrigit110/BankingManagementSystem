package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl();

        // Inject the mock repository into the private field using reflection
        Field field = AccountServiceImpl.class.getDeclaredField("accountRepository");
        field.setAccessible(true);
        field.set(accountService, accountRepository);
    }

    @Test
    void testGetAccountDetails_Found() {
        Account account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(5000));

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        Account result = accountService.getAccountDetails("1234567890");

        assertNotNull(result);
        assertEquals("1234567890", result.getAccountNumber());
        assertEquals(BigDecimal.valueOf(5000), result.getBalance());
    }

    @Test
    void testGetAccountDetails_NotFound() {
        when(accountRepository.findByAccountNumber("0000000000")).thenReturn(Optional.empty());

        Account result = accountService.getAccountDetails("0000000000");

        assertNull(result);
    }

    @Test
    void testUpdateBalance_Credit() {
        Account account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        accountService.updateBalance("1234567890", BigDecimal.valueOf(500), "CREDIT");

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(captor.capture());

        Account updated = captor.getValue();
        assertEquals(BigDecimal.valueOf(1500), updated.getBalance());
    }

    @Test
    void testUpdateBalance_Debit_Success() {
        Account account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        accountService.updateBalance("1234567890", BigDecimal.valueOf(300), "DEBIT");

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(captor.capture());

        Account updated = captor.getValue();
        assertEquals(BigDecimal.valueOf(700), updated.getBalance());
    }

    @Test
    void testUpdateBalance_Debit_InsufficientBalance() {
        Account account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(200));

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                accountService.updateBalance("1234567890", BigDecimal.valueOf(300), "DEBIT"));

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testUpdateBalance_InvalidTransactionType() {
        Account account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(BigDecimal.valueOf(1000));

        when(accountRepository.findByAccountNumber("1234567890")).thenReturn(Optional.of(account));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                accountService.updateBalance("1234567890", BigDecimal.valueOf(100), "TRANSFER"));

        assertEquals("Invalid transaction type", exception.getMessage());
    }

    @Test
    void testUpdateBalance_AccountNotFound() {
        when(accountRepository.findByAccountNumber("9999999999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                accountService.updateBalance("9999999999", BigDecimal.valueOf(100), "CREDIT"));

        assertEquals("Account not found", exception.getMessage());
    }
}
