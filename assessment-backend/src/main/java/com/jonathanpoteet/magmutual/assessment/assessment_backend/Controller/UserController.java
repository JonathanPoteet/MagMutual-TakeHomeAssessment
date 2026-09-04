package com.jonathanpoteet.magmutual.assessment.assessment_backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<Map<String, Object>>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // GET /api/users/{id} - Retrieve a user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable int id) {
        Map<String, Object> user = userService.getUserById(id);
        return user.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    // Optional:
    // 1. Ability to create and delete users
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, Object> userData) {
        Map<String, Object> createdUser = userService.createUser(userData);
        if (createdUser == null || createdUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Additionally, you can implement an update endpoint if needed:
    // @PutMapping("/users/{id}") TODO: Implement update functionality if required

    // 2. Ability to filter, sort, and search users
    /* this will be handled on the client side (frontend) for this assessment, but 
    in a real-world scenario, you can implement filtering, sorting, and searching on 
    the backend as well for reasons such as pagination for large datasets.
    */
    }
