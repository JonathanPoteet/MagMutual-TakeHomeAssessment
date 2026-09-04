package com.jonathanpoteet.magmutual.assessment.assessment_backend.Model;

import java.util.LinkedHashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User", description = "User record used for create and read operations")
public class User {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Integer id;

    @Schema(example = "Test")
    private String firstname;

    @Schema(example = "Name1")
    private String lastname;

    @Schema(example = "TestName1@gmail.com")
    private String email;

    @Schema(example = "Teacher")
    private String profession;

    @Schema(example = "2025-01-21")
    private String dateCreated;

    @Schema(example = "Turkey")
    private String country;

    @Schema(example = "Orlando")
    private String city;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("firstname", firstname);
        values.put("lastname", lastname);
        values.put("email", email);
        values.put("profession", profession);
        values.put("dateCreated", dateCreated);
        values.put("country", country);
        values.put("city", city);
        return values;
    }

    public static User fromMap(Map<String, Object> source) {
        if (source == null || source.isEmpty()) {
            return new User();
        }

        User user = new User();
        Object idValue = source.get("id");
        if (idValue != null) {
            try {
                user.setId(Integer.parseInt(String.valueOf(idValue)));
            } catch (NumberFormatException ignored) {
                // ignore invalid ID during mapping
            }
        }

        user.setFirstname(String.valueOf(source.getOrDefault("firstname", "")));
        user.setLastname(String.valueOf(source.getOrDefault("lastname", "")));
        user.setEmail(String.valueOf(source.getOrDefault("email", "")));
        user.setProfession(String.valueOf(source.getOrDefault("profession", "")));
        user.setDateCreated(String.valueOf(source.getOrDefault("dateCreated", "")));
        user.setCountry(String.valueOf(source.getOrDefault("country", "")));
        user.setCity(String.valueOf(source.getOrDefault("city", "")));
        return user;
    }
}
