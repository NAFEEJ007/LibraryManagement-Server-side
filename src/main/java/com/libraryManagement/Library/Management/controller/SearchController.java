package com.libraryManagement.Library.Management.controller;

import com.libraryManagement.Library.Management.model.Book;
import com.libraryManagement.Library.Management.model.User;
import com.libraryManagement.Library.Management.repository.BookRepository;
import com.libraryManagement.Library.Management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @GetMapping("/search")
    public ResponseEntity<SearchResponseDto> search(@RequestParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.ok(new SearchResponseDto(new ArrayList<>(), new ArrayList<>()));
        }

        List<Book> books = bookRepository.searchBooksByTitle(query.trim());
        List<User> users = userRepository.searchUsersByName(query.trim());

        List<BookResultDto> bookResults = books.stream().map(book -> {
            List<String> borrowers = book.getBorrowRecords().stream()
                    .map(br -> br.getUser().getName())
                    .distinct()
                    .collect(Collectors.toList());
            return new BookResultDto(book.getBookId(), book.getTitle(), borrowers.isEmpty() ? List.of("N/A") : borrowers);
        }).collect(Collectors.toList());

        List<UserResultDto> userResults = users.stream().map(user -> {
            List<String> borrowedBooks = user.getBorrowRecords().stream()
                    .map(br -> br.getBook().getTitle())
                    .distinct()
                    .collect(Collectors.toList());
            return new UserResultDto(user.getUserId(), user.getName(), borrowedBooks.isEmpty() ? List.of("N/A") : borrowedBooks);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new SearchResponseDto(bookResults, userResults));
    }

    @Data
    @AllArgsConstructor
    public static class SearchResponseDto {
        private List<BookResultDto> books;
        private List<UserResultDto> users;
    }

    @Data
    @AllArgsConstructor
    public static class BookResultDto {
        private int id;
        private String title;
        private List<String> borrowedBy;
    }

    @Data
    @AllArgsConstructor
    public static class UserResultDto {
        private int id;
        private String name;
        private List<String> borrowedBooks;
    }
}
