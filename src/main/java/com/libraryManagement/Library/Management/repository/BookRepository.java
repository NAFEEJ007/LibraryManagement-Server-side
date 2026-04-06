package com.libraryManagement.Library.Management.repository;

import com.libraryManagement.Library.Management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);
    long count();
    long countByActiveTrue();
    @Query("SELECT b FROM Book b WHERE b.active = true")
    List<Book> findAllActiveBooks();

    @Query("SELECT b FROM Book b WHERE b.active = true AND LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooksByTitle(@org.springframework.data.repository.query.Param("query") String query);
}
