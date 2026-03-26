package com.libraryManagement.Library.Management.service;


import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.model.User;
import com.libraryManagement.Library.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    
    // method for posting

    public Map<String, Object> addUser(User user){

        Map<String, Object> response = new HashMap<>();

        if(repo.existsByEmail(user.getEmail())){
            response.put("message","USER_ALREADY_EXISTS");
            return response;
        }

        User savedUser = repo.save(user);

        response.put("message","USER_ADDED");
        response.put("user", savedUser);

        return response;
    }
    
    // method for getting

    public List<User> getUsers(){

        return repo.findAllActiveUsers();
    }

    // 🔹 Update User
    public Map<String, Object> updateUser(int id, Map<String, String> payload) {

        Map<String, Object> response = new HashMap<>();

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Update fields
        user.setName(payload.get("name"));
        user.setEmail(payload.get("email"));
        user.setPhoneNumber(payload.get("phoneNumber"));
        user.setAddress(payload.get("address"));

        // ✅ Handle date properly
        if (payload.get("dateOfBirth") != null) {
            user.setDateOfBirth(java.time.LocalDate.parse(payload.get("dateOfBirth")));
        }

        repo.save(user);

        response.put("message", "USER_UPDATED");
        return response;
    }

    // 🔹 Soft Delete User
    public Map<String, Object> deleteUser(int id) {

        Map<String, Object> response = new HashMap<>();

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false); // ✅ SOFT DELETE
        repo.save(user);

        response.put("message", "USER_DELETED");
        return response;
    }
}
