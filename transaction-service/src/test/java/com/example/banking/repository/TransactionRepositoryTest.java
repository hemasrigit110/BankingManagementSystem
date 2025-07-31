package com.example.banking.repository;

import com.example.banking.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testSaveAndFindByFromOrToAccount() {
        Transaction transaction = new Transaction();
        transaction.setFromAccount("1234567890");
        transaction.setToAccount("0987654321");
        transaction.setAccountNumber("1234567890");
        transaction.setTransactionType("DEBIT");
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository
                .findByFromAccountOrToAccount("1234567890", "1234567890");

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        assertEquals("1234567890", transactions.get(0).getFromAccount());
    }
}
