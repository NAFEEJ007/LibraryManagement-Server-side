package com.libraryManagement.Library.Management.service;

import com.libraryManagement.Library.Management.model.Admin;
import com.libraryManagement.Library.Management.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public Map<String, Object> login(Map<String, String> payload) {

        Map<String, Object> response = new HashMap<>();

        String username = payload.get("username");
        String password = payload.get("password");

        Admin admin = adminRepo.findByUsername(username);

        if (admin == null) {
            response.put("message", "USER_NOT_FOUND");
            return response;
        }

        if (!admin.getPassword().equals(password)) {
            response.put("message", "INVALID_PASSWORD");
            return response;
        }

        response.put("message", "LOGIN_SUCCESS");
        return response;
    }
}