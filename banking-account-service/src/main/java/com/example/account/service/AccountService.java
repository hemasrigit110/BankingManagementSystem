package com.example.account.service;

import com.example.account.model.Account;

import java.math.BigDecimal;


public interface AccountService {
    Account getAccountDetails(String accountNumber);
    //Account getAccountByUserId(Long userId);
    //Account createAccount(Account account);

    void updateBalance(String accountNumber, BigDecimal amount, String transactionType);
}


    //BigDecimal getAccountBalance(String accountNumber);
    //Account getAccountByHolderName(String accountHolderName);





//package com.example.account.service;
//
//import com.example.account.exception.ResourceNotFoundException;
//import com.example.account.model.Account;
//import com.example.account.repository.AccountRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//
//@Service
//public class AccountService {
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    public Account findByAccountNumber(String accountNumber) {
//        return accountRepository.findByAccountNumber(accountNumber)
//                .orElseThrow(() -> new RuntimeException("Account not found with number: " + accountNumber));
//    }
//
////    public Optional<Account> getAccountByUsername(String username) {
////        return accountRepository.findByUsername(username);
////    }


