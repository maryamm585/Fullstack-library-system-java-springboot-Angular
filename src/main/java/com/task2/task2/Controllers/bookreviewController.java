package com.task2.task2.Controllers;

import com.task2.task2.Entities.BookReview;
import com.task2.task2.Repos.bookReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/books/reviews")
public class bookreviewController {
    @Autowired
    private bookReviewRepo bookReviewRepository;

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping("/getreviewbyid")
    public ResponseEntity<List<BookReview>> getReviewsByBookid(@RequestParam long id) {
        List<BookReview> reviews = bookReviewRepository.findByBookId(id);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody BookReview review) {
        try {
            BookReview savedReview = bookReviewRepository.save(review);
            return ResponseEntity.ok("Review  has been created.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the review.");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PutMapping("/updatereview")
    public ResponseEntity<BookReview> updateReview(@RequestParam String id, @RequestBody BookReview reviewDetails) {
        Optional<BookReview> optionalReview = bookReviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BookReview review = optionalReview.get();
        review.setRating(reviewDetails.getRating());
        review.setReviewText(reviewDetails.getReviewText());

        try {
            review = bookReviewRepository.save(review);
            System.out.println("Review updated successfully: " + review); // Logging for debug
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String id) {
        Optional<BookReview> optionalReview = bookReviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BookReview review = optionalReview.get();
        bookReviewRepository.delete(review);
        return ResponseEntity.ok("Review with id " + id + " has been deleted.");
}
}
