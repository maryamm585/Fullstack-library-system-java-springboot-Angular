package com.task2.task2.Repos;

import com.task2.task2.Entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface bookRepo extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM book WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(author) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Book> findByTitleContainingOrAuthorContaining(@Param("keyword") String keyword);
}
