package com.task2.task2.Controllers;

import com.task2.task2.Entities.*;
import com.task2.task2.Repos.bookRepo;
import com.task2.task2.Repos.bookTransactionRepo;
import com.task2.task2.Repos.userRepo;
import com.task2.task2.Services.BookTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.task2.task2.Services.bookService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class bookController {

    @Autowired
    private bookService bookservice;

    @Autowired
    private BookTransactionService bookTransactionService;

    @Autowired
    private userRepo userRepository;

    @Autowired
    private bookTransactionRepo booktransactionrepo;

    @Autowired
    private bookRepo bookRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        return bookservice.saveBook(book);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/booklist")
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = bookservice.getAllBooks(page, size); // Called on an instance, not statically
        return ResponseEntity.ok(books);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        return bookservice.searchBooks(keyword);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId, @RequestParam Long userid) {
        return bookservice.borrowBook(bookId, userid);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/borrowedBooks")
    public List<BorrowedBookDTO> getBorrowedBooks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Book> myBooks = user.getMyBooks();

        return myBooks.stream()
                .map(book -> new BorrowedBookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        null)) // Assuming you don't have transaction date in myBooks
                .collect(Collectors.toList());
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/return")
    public void returnBook(@RequestParam Long bookId, @RequestParam Long userid) {
        bookservice.returnBook(bookId, userid);
    }


    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletebook(@PathVariable long id) {
        Optional<Book> optionalbook = bookRepository.findById(id);
        if (optionalbook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bookRepository.delete(optionalbook.get());
        return ResponseEntity.ok("Book with id " + id + " has been deleted.");
    }


    public ResponseEntity<String> deleteBook(@RequestParam long id) {
        // Check if the book is currently borrowed
        List<BookTransaction> borrowedTransactions = booktransactionrepo.findByBook(id);

        if (!borrowedTransactions.isEmpty()) {
            return ResponseEntity.status(400).body("Cannot delete the book because it is currently borrowed.");
        }

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Book book = optionalBook.get();
        bookRepository.delete(book);
        return ResponseEntity.ok("Book with id " + id + " has been deleted.");
    }


}

