package com.jonathanpoteet.magmutual.assessment.assessment_backend.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.User;
import com.jonathanpoteet.magmutual.assessment.assessment_backend.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if (users == null) {
            throw new IllegalArgumentException("No users found");
        }
        return users;
    }

    public User getUserById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    // More Validation could be added here for other fields like Profession, Country, City, etc. based on requirements.
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }

        if (user.getFirstname() == null || user.getFirstname().trim().isEmpty()) {
            throw new IllegalArgumentException("Missing first name");
        }

        if (user.getLastname() == null || user.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("Missing last name");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Missing email");
        }

        String email = user.getEmail().trim();
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email");
        }

        user.setFirstname(user.getFirstname().trim());
        user.setLastname(user.getLastname().trim());
        user.setEmail(email);

        return userRepository.create(user);
    }

    public boolean deleteUserById(int id) {
        boolean deleted = userRepository.deleteById(id);
        if (!deleted) {
            throw new IllegalArgumentException("User not found");
        }
        return true;
    }

}
