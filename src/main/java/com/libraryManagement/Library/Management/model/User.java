package com.libraryManagement.Library.Management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String phoneNumber;

    private LocalDate dateOfBirth;
    private String address;
    private String email;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BorrowRecord>borrowRecords;



}
//Infinite loop in JSON --> use @JsonIgnore