package com.libraryManagement.Library.Management.repository;

import com.libraryManagement.Library.Management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActiveUsers();
    long countByActiveTrue();

    long count();

    @Query("SELECT u FROM User u WHERE u.active = true AND LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsersByName(@org.springframework.data.repository.query.Param("query") String query);
}


//existsByEmail : eta ekta method by springboot, eta check kore user ei
// same mail diye exists kortese kina, exists korle
// returns true or false
// if true: email exists and if false then email doesn't exists
