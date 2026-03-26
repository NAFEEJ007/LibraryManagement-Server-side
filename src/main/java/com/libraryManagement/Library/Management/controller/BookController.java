package com.libraryManagement.Library.Management.controller;

import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // ===========================
    // ✅ ADD BOOK
    // ===========================
    @PostMapping("/add")
    public Map<String, Object> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // ===========================
    // ✅ GET ALL ACTIVE BOOKS
    // ===========================
    @GetMapping("/bookslist")
    public List<Book> getBooks() {
        return bookService.getBooks(); // This should return only books with active = true
    }

    // ===========================
    // ✅ UPDATE BOOK
    // ===========================
    @PutMapping("/update/{id}")
    public Map<String, Object> updateBook(
            @PathVariable int id,
            @RequestBody Map<String, String> payload
    ) {
        return bookService.updateBook(id, payload);
    }

    // ===========================
    // ✅ DELETE BOOK (SOFT DELETE)
    // ===========================
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteBook(@PathVariable int id) {
        return bookService.softDeleteBook(id);
    }


}