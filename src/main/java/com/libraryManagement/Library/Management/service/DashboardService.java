package com.libraryManagement.Library.Management.service;

import com.libraryManagement.Library.Management.model.BorrowRecord;
import com.libraryManagement.Library.Management.repository.BookRepository;
import com.libraryManagement.Library.Management.repository.BorrowRecordRepository;
import com.libraryManagement.Library.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BorrowRecordRepository borrowRepo;

    // =========================
    // 📊 STATS API
    // =========================
    public Map<String, Object> getStats() {

        Map<String, Object> map = new HashMap<>();

        // ✅ Only active books
        long totalBooks = bookRepo.countByActiveTrue();

        // ✅ Borrow stats (you can later filter inactive users if needed)
        long assignedBooks = borrowRepo.countByReturnDateIsNull();
        long returnedBooks = borrowRepo.countByReturnDateIsNotNull();

        // ✅ Only active users
        long totalUsers = userRepo.countByActiveTrue();

        // =========================
        // 🔥 FIXED FINED USERS LOGIC
        // =========================
        List<BorrowRecord> allRecords = borrowRepo.findAll();
        Set<Integer> finedUsers = new HashSet<>();

        for (BorrowRecord r : allRecords) {

            // ❌ Skip deleted users
            if (!r.getUser().isActive()) continue;

            if (r.getDueDate() != null) {

                // ✅ Case 1: Returned late
                boolean isLateReturned =
                        r.getReturnDate() != null &&
                                r.getReturnDate().isAfter(r.getDueDate());

                // ✅ Case 2: Not returned but overdue
                boolean isStillLate =
                        r.getReturnDate() == null &&
                                LocalDate.now().isAfter(r.getDueDate());

                if (isLateReturned || isStillLate) {
                    finedUsers.add(r.getUser().getUserId());
                }
            }
        }

        map.put("totalBooks", totalBooks);
        map.put("assignedBooks", assignedBooks);
        map.put("returnedBooks", returnedBooks);
        map.put("totalUsers", totalUsers);
        map.put("finedUsers", finedUsers.size());

        return map;
    }

    // =========================
    // 📚 ISSUED BOOKS API
    // =========================
    public List<Map<String, Object>> getIssuedBooks() {

        List<BorrowRecord> records = borrowRepo.findByReturnDateIsNull();
        List<Map<String, Object>> result = new ArrayList<>();

        for (BorrowRecord r : records) {

            // ❌ Skip deleted users
            if (!r.getUser().isActive()) continue;

            Map<String, Object> map = new HashMap<>();

            map.put("title", r.getBook().getTitle());
            map.put("author", r.getBook().getAuthor());
            map.put("category", r.getBook().getCategory());
            map.put("isbn", r.getBook().getIsbn());
            map.put("dueDate", r.getDueDate());

            map.put("borrowerId", r.getUser().getUserId());
            map.put("borrowerName", r.getUser().getName());

            // 🔥 LIVE Fine Calculation
            long fine = 0;

            if (r.getDueDate() != null && LocalDate.now().isAfter(r.getDueDate())) {
                long daysLate = ChronoUnit.DAYS.between(r.getDueDate(), LocalDate.now());
                fine = daysLate * 5; // 5 taka per day
            }

            map.put("fine", fine);

            result.add(map);
        }

        return result;
    }
}