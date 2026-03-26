package com.libraryManagement.Library.Management.controller;

import com.libraryManagement.Library.Management.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // 📊 Stats
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return dashboardService.getStats();
    }

    // 📚 Issued Books
    @GetMapping("/issued")
    public List<Map<String, Object>> getIssuedBooks() {
        return dashboardService.getIssuedBooks();
    }
}