package com.libraryManagement.Library.Management.repository;

import com.libraryManagement.Library.Management.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);

}
