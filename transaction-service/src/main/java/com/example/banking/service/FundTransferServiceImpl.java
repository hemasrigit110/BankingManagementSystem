package com.example.banking.service;

import com.example.banking.dto.FundTransferRequest;
import com.example.banking.dto.FundTransferResponse;
import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.model.Account;

import com.example.banking.model.Transaction;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public FundTransferServiceImpl(AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    public FundTransferResponse transferFunds(FundTransferRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account fromAccount = accountRepository
                .findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));

        System.out.println(fromAccount + "FROM SERVICE");

        Account toAccount = accountRepository
                .findByAccountNumberAndIfscCode(request.getToAccount(), request.getIfscCode())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        LocalDateTime now = LocalDateTime.now();

        // Create DEBIT transaction
        Transaction debitTransaction = new Transaction();
        debitTransaction.setFromAccount(fromAccount.getAccountNumber());
        debitTransaction.setToAccount(toAccount.getAccountNumber());
        debitTransaction.setAmount(request.getAmount());
        // debitTransaction.setDescription(request.getDescription());
        debitTransaction.setTransactionType("DEBIT");
        debitTransaction.setTransactionDate(now);
        debitTransaction.setAccountNumber(fromAccount.getAccountNumber());
        transactionRepository.save(debitTransaction);

        // Create CREDIT transaction
        Transaction creditTransaction = new Transaction();
        creditTransaction.setFromAccount(fromAccount.getAccountNumber());
        creditTransaction.setToAccount(toAccount.getAccountNumber());
        creditTransaction.setAmount(request.getAmount());
        // creditTransaction.setDescription(request.getDescription());
        creditTransaction.setTransactionType("CREDIT");
        creditTransaction.setTransactionDate(now);
        creditTransaction.setAccountNumber(toAccount.getAccountNumber());
        transactionRepository.save(creditTransaction);

        return buildResponse(debitTransaction);
    }

    private FundTransferResponse buildResponse(Transaction transaction) {
        FundTransferResponse response = new FundTransferResponse();
        response.setTransactionId(transaction.getId());
        response.setStatus("SUCCESS");
        response.setMessage("Fund transfer successful");
        response.setTransactionDate(transaction.getTransactionDate());
        return response;
    }

    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
    }
}
