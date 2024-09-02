package com.task2.task2.Repos;
import com.task2.task2.Entities.Book;
import com.task2.task2.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface userRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM user WHERE LOWER(username) LIKE LOWER(CONCAT('%', :username, '%'))", nativeQuery = true)
    List<User> findByUsernameContaining(@Param("username") String username);
}

