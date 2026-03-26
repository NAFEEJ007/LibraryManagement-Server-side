package com.libraryManagement.Library.Management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String author;
    private String category;
    @Column(unique = true)
    private String isbn;

    private int totalCopies;
    private int availableCopies;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BorrowRecord>borrowRecords;


}

//Infinite loop in JSON --> use @JsonIgnore
