package org.orderapi.user.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.orderapi.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.UUID;

@SpringBootTest
public class UserRepositoryTest {

    private static final String TEST_EMAIL = "test@test.test";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = createUserWithTestValues();
        userRepository.save(user);

        Assert.isTrue(userRepository.findById(user.getId()).isPresent(),
                "user not found");
        Assert.isTrue(userRepository.findById(UUID.randomUUID()).isEmpty(),
                "non existing user found");
    }

    @Test
    public void findUserByEmail() {
        User user = createUserWithTestValues();
        userRepository.save(user);

        Assert.isTrue(userRepository.findByEmail(TEST_EMAIL).isPresent(),
                "user not found");
        Assert.isTrue(userRepository.findByEmail("nonValidEmail@Test.gr").isEmpty(),
                "non existing user found");
    }

    private User createUserWithTestValues() {
        User user = new User();
        user.setEmail(TEST_EMAIL);
        return user;
    }

    @AfterEach
    private void truncateDb() {
        userRepository.deleteAll();
    }

}
