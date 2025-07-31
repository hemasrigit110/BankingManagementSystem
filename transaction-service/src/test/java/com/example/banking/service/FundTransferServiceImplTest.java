package com.example.banking.service;

import com.example.banking.dto.FundTransferRequest;
import com.example.banking.dto.FundTransferResponse;
import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.model.Account;
import com.example.banking.model.Transaction;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FundTransferServiceImplTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private FundTransferServiceImpl fundTransferService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        fundTransferService = new FundTransferServiceImpl(accountRepository, transactionRepository);
    }

    @Test
    void testTransferFunds_Success() {
        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("123456");
        request.setToAccount("654321");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setIfscCode("TEST0001");

        Account from = new Account();
        from.setAccountNumber("123456");
        from.setBalance(BigDecimal.valueOf(2000));

        Account to = new Account();
        to.setAccountNumber("654321");
        to.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(from));
        when(accountRepository.findByAccountNumberAndIfscCode("654321", "TEST0001")).thenReturn(Optional.of(to));

        FundTransferResponse response = fundTransferService.transferFunds(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("Fund transfer successful", response.getMessage());

        verify(accountRepository, times(2)).save(any(Account.class));
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void testTransferFunds_InsufficientBalance() {
        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("123456");
        request.setToAccount("654321");
        request.setAmount(BigDecimal.valueOf(3000));
        request.setIfscCode("TEST0001");

        Account from = new Account();
        from.setAccountNumber("123456");
        from.setBalance(BigDecimal.valueOf(2000));

        Account to = new Account();
        to.setAccountNumber("654321");
        to.setBalance(BigDecimal.valueOf(500));

        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(from));
        when(accountRepository.findByAccountNumberAndIfscCode("654321", "TEST0001")).thenReturn(Optional.of(to));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                fundTransferService.transferFunds(request));
        assertEquals("Insufficient balance", ex.getMessage());
    }

    @Test
    void testTransferFunds_SourceAccountNotFound() {
        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("000000");
        request.setToAccount("654321");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setIfscCode("TEST0001");

        when(accountRepository.findByAccountNumber("000000")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> fundTransferService.transferFunds(request));
    }

    @Test
    void testTransferFunds_DestinationAccountNotFound() {
        FundTransferRequest request = new FundTransferRequest();
        request.setFromAccount("123456");
        request.setToAccount("999999");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setIfscCode("TEST0001");

        Account from = new Account();
        from.setAccountNumber("123456");
        from.setBalance(BigDecimal.valueOf(2000));

        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(from));
        when(accountRepository.findByAccountNumberAndIfscCode("999999", "TEST0001")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> fundTransferService.transferFunds(request));
    }

    @Test
    void testGetTransactionHistory() {
        Transaction txn1 = new Transaction();
        txn1.setTransactionType("DEBIT");

        Transaction txn2 = new Transaction();
        txn2.setTransactionType("CREDIT");

        when(transactionRepository.findByFromAccountOrToAccount("123456", "123456"))
                .thenReturn(List.of(txn1, txn2));

        List<Transaction> result = fundTransferService.getTransactionHistory("123456");

        assertEquals(2, result.size());
    }
}
