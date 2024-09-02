package com.task2.task2.Repos;

import com.task2.task2.Entities.BookReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface bookReviewRepo extends MongoRepository<BookReview, String> {
    List<BookReview> findByBookId(Long bookId);

}
