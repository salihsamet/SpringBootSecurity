package com.clinked.articleservice.unitTest.repository;

import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user = null;

    @BeforeEach
    void setUser(){ user = new User("user1", "password1", Role.USER); }

    @AfterEach
    void clearDb(){
        userRepository.deleteAllInBatch();
    }

    @Test
    void saveUser() {
        User createdUser = userRepository.save(user);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getPassword(), createdUser.getPassword());
        assertEquals(user.getRole(), createdUser.getRole());
    }

    @Test
    void findUser() {
        userRepository.save(user);
        Optional<User> findUser = userRepository.findByUserName(user.getUsername());
        assertEquals(user.getUsername(), findUser.get().getUsername());
        assertEquals(user.getPassword(), findUser.get().getPassword());
        assertEquals(user.getRole(), findUser.get().getRole());
    }
}