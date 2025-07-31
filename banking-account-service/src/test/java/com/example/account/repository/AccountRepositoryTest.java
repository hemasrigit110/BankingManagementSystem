package com.example.account.repository;

import com.example.account.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindByAccountNumber_Found() {
        Account account = new Account();
        account.setAccountHolderName("Test User");
        account.setAccountNumber("ACC123456");
        account.setIfscCode("IFSC001");
        account.setBalance(BigDecimal.valueOf(1000));

        accountRepository.save(account);

        Optional<Account> result = accountRepository.findByAccountNumber("ACC123456");
        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getAccountHolderName());
    }

    @Test
    void testFindByAccountNumber_NotFound() {
        Optional<Account> result = accountRepository.findByAccountNumber("NON_EXISTENT");
        assertFalse(result.isPresent());
    }

    @Test
    void testExistsByAccountNumber_True() {
        Account account = new Account();
        account.setAccountHolderName("User Exists");
        account.setAccountNumber("EXIST123");
        account.setIfscCode("IFSC002");
        account.setBalance(BigDecimal.valueOf(2000));

        accountRepository.save(account);

        boolean exists = accountRepository.existsByAccountNumber("EXIST123");
        assertTrue(exists);
    }

    @Test
    void testExistsByAccountNumber_False() {
        boolean exists = accountRepository.existsByAccountNumber("DOES_NOT_EXIST");
        assertFalse(exists);
    }
}
