package com.task2.task2.Repos;

import com.task2.task2.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface roleRepo extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role WHERE name = :name", nativeQuery = true)
    Role findByName(@Param("name") String rolename);

}
