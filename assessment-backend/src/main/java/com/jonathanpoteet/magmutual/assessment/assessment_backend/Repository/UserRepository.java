package com.jonathanpoteet.magmutual.assessment.assessment_backend.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.User;
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

    public List<User> findAll() {
        String sql = "SELECT id, firstname, lastname, email, profession, dateCreated, country, city FROM users ORDER BY id";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User findById(int id) {
        String sql = "SELECT id, firstname, lastname, email, profession, dateCreated, country, city FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return users.isEmpty() ? null : users.get(0);
    }

    public User create(User user) {
        String sql = "INSERT INTO users (firstname, lastname, email, profession, dateCreated, country, city) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql,
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getProfession(),
            user.getDateCreated(),
            user.getCountry(),
            user.getCity()
        );

        if (result == 0) {
            return null;
        }

        Integer generatedId = jdbcTemplate.queryForObject("SELECT last_insert_rowid()", Integer.class);
        return findById(generatedId == null ? 0 : generatedId);
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            //this is mapping the information from the database to the User object
            user.setId(rs.getInt("id"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setProfession(rs.getString("profession"));
            user.setDateCreated(rs.getString("dateCreated"));
            user.setCountry(rs.getString("country"));
            user.setCity(rs.getString("city"));
            return user;
        }
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
