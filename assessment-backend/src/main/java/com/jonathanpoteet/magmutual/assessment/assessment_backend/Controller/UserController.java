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

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.RecentSignups;
import com.jonathanpoteet.magmutual.assessment.assessment_backend.Model.User;
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
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // GET /api/users/{id} - Retrieve a user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Optional:
    // 1. Ability to create and delete users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // An endpoint to get the recent signups trends, returning the 25 most recent signups
    @GetMapping("/users/trends")
    public ResponseEntity<List<RecentSignups>> getTrends() {
        return ResponseEntity.ok(userService.getTrends());
    }

    // Additionally, you can implement an update endpoint if needed:
    // @PutMapping("/users/{id}") TODO: Implement update functionality if required

    // 2. Ability to filter, sort, and search users
    /* this will be handled on the client side (frontend) for this assessment, but 
    in a real-world scenario, you can implement filtering, sorting, and searching on 
    the backend as well for reasons such as pagination for large datasets.

    filter, sorting, and search can be much more costly on the frontend if the dataset grows larger.

    */
    }
