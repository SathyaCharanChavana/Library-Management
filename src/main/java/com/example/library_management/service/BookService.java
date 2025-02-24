package com.example.library_management.service;

import com.example.library_management.model.Book;
import com.example.library_management.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }


    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setTotalCopies(updatedBook.getTotalCopies());
            existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
            return bookRepository.save(existingBook);
        }
        return null; // Or throw exception
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBookPartially(Long id, Book updates) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Apply only non-null updates
        if (updates.getTitle() != null) {
            book.setTitle(updates.getTitle());
        }
        if (updates.getAuthor() != null) {
            book.setAuthor(updates.getAuthor());
        }
        if (updates.getIsbn() != null) {
            book.setIsbn(updates.getIsbn());
        }
        if (updates.getGenre() != null) {
            book.setGenre(updates.getGenre());
        }
        if (updates.getPrice() != null) {
            book.setPrice(updates.getPrice());
        }
        if (updates.getTotalCopies() != 0) {
            book.setTotalCopies(updates.getTotalCopies());
        }
        if (updates.getAvailableCopies() != 0) {
            book.setAvailableCopies(updates.getAvailableCopies());
        }

        return bookRepository.save(book);
    }
}
