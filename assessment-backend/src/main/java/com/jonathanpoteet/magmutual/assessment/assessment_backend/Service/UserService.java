package com.jonathanpoteet.magmutual.assessment.assessment_backend.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jonathanpoteet.magmutual.assessment.assessment_backend.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getUsers() {
        return userRepository.findAll();
    }

    public Map<String, Object> getUserById(int id) {
        return userRepository.findById(id);
    }
}
