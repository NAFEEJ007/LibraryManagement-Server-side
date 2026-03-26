package com.libraryManagement.Library.Management.controller;

import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.model.User;
import com.libraryManagement.Library.Management.service.UserService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")


public class UserController {
@Autowired
private UserService service;
    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody User user){

        return service.addUser(user);
    }

    @GetMapping("/userslist")
    public List<User> getUsers(){

        return service.getUsers();
    }
    // 🔹 Update User
    @PutMapping("/update/{id}")
    public Map<String, Object> updateUser(
            @PathVariable int id,
            @RequestBody Map<String, String> payload
    ) {
        return service.updateUser(id, payload);
    }

    // 🔹 Delete User
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }
}



//When a POST request comes in,
// Spring automatically converts the JSON into a User object using @RequestBody.
