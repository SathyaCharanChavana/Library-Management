package com.example.library_management.controller;

import com.example.library_management.model.Transaction;
import com.example.library_management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/issue")
    public Transaction issueBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return transactionService.issueBook(bookId, memberId);
    }

    @PostMapping("/return/{transactionId}")
    public Transaction returnBook(@PathVariable Long transactionId) {
        return transactionService.returnBook(transactionId);
    }

    @GetMapping("/member/{memberId}")
    public List<Transaction> getTransactionsByMember(@PathVariable Long memberId) {
        return transactionService.getTransactionsByMember(memberId);
    }
}

