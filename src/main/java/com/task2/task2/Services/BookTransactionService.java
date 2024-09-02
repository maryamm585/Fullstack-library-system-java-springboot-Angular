package com.task2.task2.Services;

import com.task2.task2.Entities.BookTransaction;
import com.task2.task2.Repos.bookTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTransactionService {

    @Autowired
    private bookTransactionRepo bookTransactionRepository;

    public List<BookTransaction> getBorrowedBooksByUserId(Long userId) {
        return bookTransactionRepository.findByUser(userId);
    }
}
