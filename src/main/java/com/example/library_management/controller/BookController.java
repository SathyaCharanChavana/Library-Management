package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateInBook(@PathVariable Long id, @RequestBody Book updates) {
        Book updatedBook = bookService.updateBookPartially(id, updates);
        return ResponseEntity.ok(updatedBook);
    }

    @PersistenceContext
    private EntityManager entityManager;

    // 1. List of books where availableCopies < 10 - JPQL
    @GetMapping("/low-availability")
    public List<Book> getBooksWithLowAvailability() {
        TypedQuery<Book> jpqlQuery = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.availableCopies < :count", Book.class);
        jpqlQuery.setParameter("count", 10);
        return jpqlQuery.getResultList();
    }

    // 2. List of books by Author and Genre - Native SQL Query
    @GetMapping("/by-author-genre")
    public List<Book> getBooksByAuthorAndGenre(
            @RequestParam("author") String author,
            @RequestParam("genre") String genre) {
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT * FROM books WHERE author = :author AND genre = :genre", Book.class);
        nativeQuery.setParameter("author", author);
        nativeQuery.setParameter("genre", genre);
        @SuppressWarnings("unchecked")
        List<Book> books = nativeQuery.getResultList();
        return books;
    }

    // 3. Count of books written by Author - Named Query
    // Add this to your Book entity:
    /*
    @Entity
    @Table(name = "books")
    @NamedQuery(
        name = "Book.countByAuthor",
        query = "SELECT COUNT(b) FROM Book b WHERE b.author = :author"
    )
    public class Book { ... }
    */
    @GetMapping("/count-by-author")
    public ResponseEntity<Long> getBookCountByAuthor(@RequestParam("author") String author) {
        TypedQuery<Long> namedQuery = entityManager.createNamedQuery("Book.countByAuthor", Long.class);
        namedQuery.setParameter("author", author);
        Long count = namedQuery.getSingleResult();
        return ResponseEntity.ok(count);
    }
}

