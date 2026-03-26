package com.libraryManagement.Library.Management.service;

import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.model.BorrowRecord;
import com.libraryManagement.Library.Management.model.User;
import com.libraryManagement.Library.Management.repository.BookRepository;
import com.libraryManagement.Library.Management.repository.BorrowRecordRepository;
import com.libraryManagement.Library.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    // ===========================
    // 📌 ASSIGN BOOK
    // ===========================
    @Transactional
    public Map<String, Object> assignBook(Map<String, String> payload) {

        Map<String, Object> response = new HashMap<>();

        int userId = Integer.parseInt(payload.get("userId"));
        int bookId = Integer.parseInt(payload.get("bookId"));

        // 1️⃣ Check if already assigned
        boolean alreadyAssigned = borrowRepo
                .existsByUser_UserIdAndBook_BookIdAndReturnDateIsNull(userId, bookId);

        if (alreadyAssigned) {
            response.put("message", "BOOK_ALREADY_ASSIGNED");
            return response;
        }

        // 2️⃣ Get User & Book
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 3️⃣ Check copies
        if (book.getAvailableCopies() <= 0) {
            response.put("message", "NO_COPIES_AVAILABLE");
            return response;
        }

        // 4️⃣ Create record
        BorrowRecord record = new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setNotes(payload.get("notes"));

        if (payload.get("issueDate") != null && !payload.get("issueDate").isEmpty()) {
            record.setIssueDate(LocalDate.parse(payload.get("issueDate")));
        }

        if (payload.get("dueDate") != null && !payload.get("dueDate").isEmpty()) {
            record.setDueDate(LocalDate.parse(payload.get("dueDate")));
        }

        // ❗ IMPORTANT: returnDate must be NULL when assigning
        record.setReturnDate(null);

        // 5️⃣ Save record
        BorrowRecord savedRecord = borrowRepo.save(record);

        // 6️⃣ Decrease copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        response.put("message", "BOOK_ASSIGNED");
        response.put("borrowRecord", savedRecord);

        return response;
    }

    // ===========================
    // 📌 RETURN BOOK
    // ===========================
    @Transactional
    public Map<String, Object> returnBook(Map<String, String> payload) {

        Map<String, Object> response = new HashMap<>();

        int borrowId = Integer.parseInt(payload.get("borrowId"));
        LocalDate actualReturnDate = LocalDate.parse(payload.get("returnDate"));

        // 1️⃣ Find record
        BorrowRecord record = borrowRepo.findById(borrowId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        // 2️⃣ Check already returned
        if (record.getReturnDate() != null) {
            response.put("message", "BOOK_ALREADY_RETURNED");
            return response;
        }

        // 3️⃣ Get due date (expected return date)
        LocalDate dueDate = record.getDueDate();

        boolean isLate = false;
        long fine = 0;

        // 4️⃣ Late check
        if (dueDate != null && actualReturnDate.isAfter(dueDate)) {
            isLate = true;

            long daysLate = ChronoUnit.DAYS.between(dueDate, actualReturnDate);
            fine = daysLate * 5; // 🔥 customize fine per day

            record.setNotes("Late return! Fine: " + fine + " taka");
        }

        // 5️⃣ Set actual return date
        record.setReturnDate(actualReturnDate);

        // 6️⃣ Save record
        borrowRepo.save(record);

        // 7️⃣ Increase available copies
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        // 8️⃣ Response
        response.put("message", "BOOK_RETURNED");
        response.put("isLate", isLate);
        response.put("fine", fine);
        response.put("borrowRecord", record);

        return response;
    }

    // ===========================
    // 📌 GET BORROWED BOOKS BY USER
    // ===========================
    public List<Map<String, Object>> getBorrowedBooksByUser(int userId) {

        List<BorrowRecord> records =
                borrowRepo.findByUser_UserIdAndReturnDateIsNull(userId);

        List<Map<String, Object>> result = new ArrayList<>();

        for (BorrowRecord record : records) {
            Map<String, Object> map = new HashMap<>();

            map.put("borrowId", record.getBorrowId());
            map.put("bookId", record.getBook().getBookId());
            map.put("title", record.getBook().getTitle());
            map.put("dueDate", record.getDueDate());

            result.add(map);
        }

        return result;
    }
}