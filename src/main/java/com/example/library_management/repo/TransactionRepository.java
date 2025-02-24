package com.example.library_management.repo;
import com.example.library_management.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBookId(Long bookId);

    List<Transaction> findByMemberId(Long memberId);
}