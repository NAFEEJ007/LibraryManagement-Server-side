package com.libraryManagement.Library.Management.service;

import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepo;

    //method for posting
    public Map<String, Object> addBook(Book book){
        Map<String, Object> response = new HashMap<>();
        if (bookRepo.existsByIsbn(book.getIsbn())){
            response.put("message","BOOK_ALREADY_EXISTS");
            return response;
        }
        Book savedBook = bookRepo.save(book);

        response.put("message","BOOK_ADDED");
        response.put("book", savedBook);

        return response;
    }

    public List<Book> getBooks() {
        return bookRepo.findAllActiveBooks(); // Use the new query
    }

    public Map<String, Object> updateBook(int id, Map<String, String> payload) {

        Map<String, Object> response = new HashMap<>();

        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 🔹 Update fields
        book.setTitle(payload.get("title"));
        book.setAuthor(payload.get("author"));
        book.setCategory(payload.get("category"));
        book.setIsbn(payload.get("isbn"));
        book.setTotalCopies(Integer.parseInt(payload.get("totalCopies")));
        book.setAvailableCopies(Integer.parseInt(payload.get("availableCopies")));

        bookRepo.save(book);

        response.put("message", "BOOK_UPDATED");
        return response;
    }

// softDelete
public Map<String, Object> softDeleteBook(int id) {
    Map<String, Object> response = new HashMap<>();

    Book book = bookRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    book.setActive(false); // Soft delete
    bookRepo.save(book);

    response.put("message", "BOOK_DELETED");
    return response;
}



}
