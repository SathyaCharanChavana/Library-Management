package com.example.library_management.repo;
import com.example.library_management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Custom query methods can be added if needed
    Member findByEmail(String email);
}
//changes to merge feature branch to main branch