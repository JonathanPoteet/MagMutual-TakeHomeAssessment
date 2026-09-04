package com.jonathanpoteet.magmutual.assessment.assessment_backend.StartupHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartupHelperService {

    private final JdbcTemplate jdbcTemplate;

    public StartupHelperService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void seedDataIfEmpty() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        if (count != null && count > 0) {
            return;
        }

        Path csvPath = resolveCsvPath();
        if (Files.notExists(csvPath)) {
            return;
        }

        List<Map<String, Object>> users = loadUsersFromCsv(csvPath);
        if (users.isEmpty()) {
            return;
        }

        String sql = "INSERT OR IGNORE INTO users (id, firstname, lastname, email, profession, dateCreated, country, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map<String, Object> user = users.get(i);
                ps.setInt(1, ((Number) user.get("id")).intValue());
                ps.setString(2, String.valueOf(user.getOrDefault("firstname", "")));
                ps.setString(3, String.valueOf(user.getOrDefault("lastname", "")));
                ps.setString(4, String.valueOf(user.getOrDefault("email", "")));
                ps.setString(5, String.valueOf(user.getOrDefault("profession", "")));
                ps.setString(6, String.valueOf(user.getOrDefault("dateCreated", "")));
                ps.setString(7, String.valueOf(user.getOrDefault("country", "")));
                ps.setString(8, String.valueOf(user.getOrDefault("city", "")));
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }
    // parses the CSV file and returns a list of user data maps
    private List<Map<String, Object>> loadUsersFromCsv(Path csvPath) {
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                return List.of();
            }

            List<String> headers = parseCsvLine(headerLine);
            List<Map<String, Object>> users = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                List<String> values = parseCsvLine(line);
                Map<String, Object> user = new LinkedHashMap<>();

                for (int i = 0; i < Math.min(headers.size(), values.size()); i++) {
                    String key = headers.get(i).trim();
                    String value = values.get(i).trim();
                    if ("id".equalsIgnoreCase(key)) {
                        user.put("id", Integer.parseInt(value));
                    } else {
                        user.put(key, value);
                    }
                }

                if (!user.isEmpty()) {
                    users.add(user);
                }
            }

            return users;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read user CSV from " + csvPath, e);
        }
    }

    //used to find the initial csv file to seed the database with user data
    private Path resolveCsvPath() {
        String workingDir = System.getProperty("user.dir");
        return Path.of(workingDir, "..", "UserInformation.csv").normalize();
    }

    // Parses a single CSV line into a list of values, handling quoted values and commas within quotes
    private List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (ch == ',' && !insideQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        values.add(current.toString());
        return values;
    }
}
