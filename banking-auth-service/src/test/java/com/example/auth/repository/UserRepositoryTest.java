package com.example.auth.repository;

import com.example.auth.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Ensures changes are rolled back after each test
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");

        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    public void testExistsByUsername() {
        User user = new User();
        user.setUsername("uniqueuser");
        user.setPassword("secret");

        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("uniqueuser");
        assertTrue(exists);

        boolean notExists = userRepository.existsByUsername("nonexistent");
        assertFalse(notExists);
    }
}
