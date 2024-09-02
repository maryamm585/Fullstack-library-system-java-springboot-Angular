package com.task2.task2.Repos;

import com.task2.task2.Entities.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bookTransactionRepo extends JpaRepository<BookTransaction, Long> {

    @Query(value = "SELECT * FROM book_transaction WHERE user_id = :userId", nativeQuery = true)
    List<BookTransaction> findByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM book_transaction WHERE book_id = :bookId", nativeQuery = true)
    List<BookTransaction> findByBook(@Param("bookId") Long bookId);

    @Query("SELECT bt FROM BookTransaction bt WHERE bt.book.id = :bookId AND bt.user.id = :userId AND bt.transactionType = :transactionType")
    List<BookTransaction> findByBookAndUserAndTransactionType(@Param("bookId") Long bookId, @Param("userId") Long userId, @Param("transactionType") String transactionType);


}
