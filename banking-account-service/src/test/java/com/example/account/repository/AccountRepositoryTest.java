package com.example.account.repository;

import com.example.account.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindByAccountNumber() {
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(new BigDecimal("1000.00"));
        accountRepository.save(account);

        Optional<Account> result = accountRepository.findByAccountNumber("ACC123");

        assertTrue(result.isPresent());
        assertEquals("ACC123", result.get().getAccountNumber());
        assertEquals(new BigDecimal("1000.00"), result.get().getBalance());
    }

    @Test
    void testExistsByAccountNumber() {
        Account account = new Account();
        account.setAccountNumber("ACC456");
        account.setBalance(new BigDecimal("2000.00"));
        accountRepository.save(account);

        boolean exists = accountRepository.existsByAccountNumber("ACC456");

        assertTrue(exists);
    }
}
