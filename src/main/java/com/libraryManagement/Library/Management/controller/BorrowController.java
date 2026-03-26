package com.libraryManagement.Library.Management.controller;

import com.libraryManagement.Library.Management.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // ===========================
    // 📌 ASSIGN BOOK
    // ===========================
    @PostMapping("/assign")
    public Map<String, Object> assignBook(@RequestBody Map<String, String> payload) {
        return borrowService.assignBook(payload);
    }

    // ===========================
    // 📌 RETURN BOOK
    // ===========================
    @PostMapping("/return")
    public Map<String, Object> returnBook(@RequestBody Map<String, String> payload) {
        return borrowService.returnBook(payload);
    }

    // ===========================
    // 📌 GET BORROWED BOOKS BY USER
    // ===========================
    @GetMapping("/user/{userId}")
    public List<Map<String, Object>> getBorrowedBooks(@PathVariable int userId) {
        return borrowService.getBorrowedBooksByUser(userId);
    }
}