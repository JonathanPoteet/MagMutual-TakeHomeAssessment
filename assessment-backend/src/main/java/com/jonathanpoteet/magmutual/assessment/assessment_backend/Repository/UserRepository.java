package com.jonathanpoteet.magmutual.assessment.assessment_backend.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.StartupHelper.StartupHelperService;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StartupHelperService startupHelperService;

    public UserRepository(DataSource dataSource, StartupHelperService startupHelperService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.startupHelperService = startupHelperService;
        initializeDatabase();
    }

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT id, firstname, lastname, email, profession, dateCreated, country, city FROM users ORDER BY id";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> findById(int id) {
        String sql = "SELECT id, firstname, lastname, email, profession, dateCreated, country, city FROM users WHERE id = ?";
        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, id);
        return users.isEmpty() ? Map.of() : users.get(0);
    }

    public Map<String, Object> create(Map<String, Object> userData) {
        String sql = "INSERT INTO users (firstname, lastname, email, profession, dateCreated, country, city) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,
            userData.getOrDefault("firstname", ""),
            userData.getOrDefault("lastname", ""),
            userData.getOrDefault("email", ""),
            userData.getOrDefault("profession", ""),
            userData.getOrDefault("dateCreated", ""),
            userData.getOrDefault("country", ""),
            userData.getOrDefault("city", "")
        );

        if (result == 0) {
            return Map.of();
        }

        Integer generatedId = jdbcTemplate.queryForObject("SELECT last_insert_rowid()", Integer.class);
        return findById(generatedId == null ? 0 : generatedId);
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private void initializeDatabase() {
        Path dataDirectory = Path.of(System.getProperty("user.dir"), "data");
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create SQLite data directory", e);
        }

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY,
                firstname TEXT,
                lastname TEXT,
                email TEXT,
                profession TEXT,
                dateCreated TEXT,
                country TEXT,
                city TEXT
            )
        """);

        startupHelperService.seedDataIfEmpty();
    }
}
