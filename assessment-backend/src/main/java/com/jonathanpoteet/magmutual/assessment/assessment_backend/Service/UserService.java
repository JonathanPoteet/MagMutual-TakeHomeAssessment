package com.jonathanpoteet.magmutual.assessment.assessment_backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.RecentSignups;
import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.User;
import com.jonathanpoteet.magmutual.assessment.assessment_backend.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        /* 
        this dataset could be cached in memory for performance
        but it depends on the size, update frequency, and
        security requirements.
        */
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


    public List<RecentSignups> getTrends() {
        /* 
        Since getUsers is already planned to be called and this is currently 
        a small dataset, we can sort and limit the results here. 
        In a real-world scenario, this can be done in another database 
        query for efficiency or on a cached dataset.

        This also depends on the type of data that is being handled.
        If it is sensitive data, we might be more cautious about
        caching or storing it in memory depending on the security requirements.
        */
        return getUsers().stream()
        .sorted((u1, u2) -> u2.getDateCreated().compareTo(u1.getDateCreated()))
        .limit(25)
        .map(u -> new RecentSignups(
            u.getDateCreated(),
            u.getCity(),
            u.getProfession()
        ))
        .toList();
    }
}
