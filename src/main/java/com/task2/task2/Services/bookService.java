package com.task2.task2.Services;

import com.task2.task2.Entities.Book;
import com.task2.task2.Entities.BookTransaction;
import com.task2.task2.Entities.User;
import com.task2.task2.Repos.bookRepo;
import com.task2.task2.Repos.bookTransactionRepo;
import com.task2.task2.Repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class bookService {

    @Autowired
    private bookRepo bookRepository;

    @Autowired
    private userRepo userRepository;

    @Autowired
    private bookTransactionRepo bookTransactionRepository;

    public ResponseEntity<String> saveBook(Book book) {
        try {
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.ok("book  has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public Page<Book> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    public ResponseEntity<List<Book>> searchBooks(String keyword) {
        List<Book> books = bookRepository.findByTitleContainingOrAuthorContaining(keyword);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    public ResponseEntity<String> borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        if (book.getCopiesAvailable() <= 0) {
            return new ResponseEntity<>("No copies available", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        if (user.getMyBooks().contains(book)) {
            return new ResponseEntity<>("Book already borrowed", HttpStatus.BAD_REQUEST);
        }

        // Record the borrow transaction
        user.addBookToMyBooks(book);
        userRepository.save(user);

        BookTransaction transaction = new BookTransaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setTransactionType("BORROW");
        transaction.setTransactionDate(new Date());
        bookTransactionRepository.save(transaction);


        // Decrease the number of available copies
        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);

        return ResponseEntity.ok("Book borrowed successfully");
    }



    public ResponseEntity<String> returnBook(Long bookId, long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // Increase the number of available copies
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        // Fetch the user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));


        if (user.getMyBooks().contains(book)) {
            user.removeBookFromMyBooks(book);
            userRepository.save(user);
        } else {
            return new ResponseEntity<>("Book was not borrowed by the user", HttpStatus.BAD_REQUEST);
        }

        List<BookTransaction> existingTransactions = bookTransactionRepository.findByBookAndUserAndTransactionType(bookId, userId, "RETURN");

        if (existingTransactions.isEmpty()) {
            BookTransaction transaction = new BookTransaction();
            transaction.setBook(book);
            transaction.setUser(user);
            transaction.setTransactionType("RETURN");
            transaction.setTransactionDate(new Date());
            bookTransactionRepository.save(transaction);

            return ResponseEntity.ok("Book returned successfully");
        } else {
            return new ResponseEntity<>("This book has already been returned by the user", HttpStatus.BAD_REQUEST);
        }
    }
}
