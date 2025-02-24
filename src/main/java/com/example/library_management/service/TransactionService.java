package com.example.library_management.service;
import com.example.library_management.model.Book;
import com.example.library_management.model.Member;
import com.example.library_management.model.Transaction;
import com.example.library_management.repo.BookRepository;
import com.example.library_management.repo.MemberRepository;
import com.example.library_management.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
//changes to merge feature branch to main branch

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Transaction issueBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);
        if (book != null && member != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1); // Decrease available copies
            bookRepository.save(book);

            Transaction transaction = new Transaction();
            transaction.setBook(book);
            transaction.setMember(member);
            transaction.setIssueDate(new java.util.Date());
            transaction.setDueDate(calculateDueDate());
            return transactionRepository.save(transaction);
        }
        return null;
    }


    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        if (transaction != null) {
            Book book = transaction.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
            transaction.setReturnDate(new java.util.Date());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public List<Transaction> getTransactionsByMember(Long memberId) {
        return transactionRepository.findByMemberId(memberId);
    }


    private Date calculateDueDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 14);
        return calendar.getTime();
    }
}

