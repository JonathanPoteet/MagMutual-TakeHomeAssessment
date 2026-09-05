package com.jonathanpoteet.magmutual.assessment.assessment_backend.Model;


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

}
