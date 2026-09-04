package com.jonathanpoteet.magmutual.assessment.assessment_backend.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUsersReturnsUsers() {
        assertNotNull(userService.getUsers());
        assertFalse(userService.getUsers().isEmpty());
    }

    @Test
    void sqliteDatabaseIsCreatedForUserStorage() {
        Path databasePath = Path.of(System.getProperty("user.dir"), "data", "users.db");
        assertTrue(Files.exists(databasePath), "Expected SQLite database file to be initialized at data/users.db");
    }
}
