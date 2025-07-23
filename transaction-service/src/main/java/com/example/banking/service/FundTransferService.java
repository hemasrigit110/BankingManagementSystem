
package com.example.banking.service;

import com.example.banking.dto.FundTransferRequest;
import com.example.banking.dto.FundTransferResponse;
import com.example.banking.model.Transaction;

import java.util.List;

public interface FundTransferService {
    FundTransferResponse transferFunds(FundTransferRequest request);
    List<Transaction> getTransactionHistory(String accountNumber);

}
