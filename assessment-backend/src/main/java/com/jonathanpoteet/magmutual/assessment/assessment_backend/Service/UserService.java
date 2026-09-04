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
        return userRepository.findAll()
            .stream()
            .map(User::fromMap)
            .toList();
    }

    public User getUserById(int id) {
        Map<String, Object> userData = userRepository.findById(id);
        return userData.isEmpty() ? null : User.fromMap(userData);
    }

    public User createUser(User user) {
        if (user == null) {
            return null;
        }

        Map<String, Object> createdUser = userRepository.create(user.toMap());
        return createdUser == null || createdUser.isEmpty() ? null : User.fromMap(createdUser);
    }

    public boolean deleteUserById(int id) {
        return userRepository.deleteById(id);
    }

}
