package com.example.account.controller;

import com.example.account.dto.UpdateBalanceRequest;
import com.example.account.model.Account;
import com.example.account.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<?> getAccountDetails(@PathVariable String accountNumber) {
        Account account = accountService.getAccountDetails(accountNumber);
        if (account == null) {
            return ResponseEntity.ok("No account found with account number: " + accountNumber);
        }
        // account.setBalance(new BigDecimal("10000.00")); // Example balance
        // account.setAccountType("Savings");
        return ResponseEntity.ok(account);
    }


    @PostMapping("/update-balance")
    public ResponseEntity<String> updateBalance(@RequestBody UpdateBalanceRequest request) {

        System.out.println(request);
        try {
            accountService.updateBalance(
                    request.getAccountNumber(),
                    request.getAmount(),
                    request.getTransactionType());
            return ResponseEntity.ok("Balance updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}













