package com.jonathanpoteet.magmutual.assessment.assessment_backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // GET /api/users - Retrieve all users
    @GetMapping("/users")
    public List<Map<String, Object>> getUsers() {
        return userService.getUsers();
    }
    // GET /api/users/{id} - Retrieve a user by ID
    @GetMapping("/users/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Optional:
    // 1. Ability to create and delete users
    @PostMapping("/users")
    public Map<String, Object> createUser(Map<String, Object> userData) {
        // Implementation for creating a new user
        return Map.of();
    }
    
    @DeleteMapping("/users/{id}")
    public Map<String, Object> deleteUser(@PathVariable int id) {
        // Implementation for deleting a user
        return Map.of();
    }

    // Additionally, you can implement an update endpoint if needed:
    @PutMapping("/users/{id}")
    public Map<String, Object> updateUser(@PathVariable int id, Map<String, Object> userData) {
        // Implementation for updating a user
        return Map.of();
    }

    // 2. Ability to filter, sort, and search users
    /* this will be handled on the client side (frontend) for this assessment, but 
    in a real-world scenario, you can implement filtering, sorting, and searching on 
    the backend as well for reasons such as pagination for large datasets.
    */
    }
