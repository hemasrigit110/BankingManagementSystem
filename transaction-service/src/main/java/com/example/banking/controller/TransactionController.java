package com.example.banking.controller;

import com.example.banking.dto.FundTransferRequest;
import com.example.banking.dto.FundTransferResponse;
import com.example.banking.model.Transaction;
import com.example.banking.service.FundTransferService;
import com.example.banking.service.FundTransferServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private FundTransferServiceImpl fundTransferService;

    @PostMapping("/transfer")
    public ResponseEntity<FundTransferResponse> transferFunds(@RequestBody FundTransferRequest request) {
        System.out.println(request.getFromAccount() + "HERE");
        FundTransferResponse response = fundTransferService.transferFunds(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String accountNumber) {
        List<Transaction> transactions = fundTransferService.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}
