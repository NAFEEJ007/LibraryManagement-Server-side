package com.libraryManagement.Library.Management.repository;

import com.libraryManagement.Library.Management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {
    boolean existsByUser_UserIdAndBook_BookIdAndReturnDateIsNull(int userId, int bookId);
    List<BorrowRecord> findByUser_UserIdAndReturnDateIsNull(int userId);

    // COUNT
    long countByReturnDateIsNull();      // currently assigned
    long countByReturnDateIsNotNull();   // returned books

    // LIST
    List<BorrowRecord> findByReturnDateIsNull(); // currently issued books

}
