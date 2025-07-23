package com.example.account.service;

import com.example.account.model.Account;

import com.example.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountDetails(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        return optionalAccount.orElse(null);  // Return null if not found
    }

    @Override
    public void updateBalance(String accountNumber, BigDecimal amount, String transactionType) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal currentBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

        if ("DEBIT".equalsIgnoreCase(transactionType)) {
            if (currentBalance.compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }
            account.setBalance(currentBalance.subtract(amount));
        } else if ("CREDIT".equalsIgnoreCase(transactionType)) {
            account.setBalance(currentBalance.add(amount));
        } else {
            throw new RuntimeException("Invalid transaction type");
        }

        accountRepository.save(account);
    }
}




















