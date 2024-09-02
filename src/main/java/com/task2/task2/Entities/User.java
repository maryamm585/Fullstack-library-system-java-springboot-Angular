package com.task2.task2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mysql.cj.conf.PropertyKey.logger;

@Entity
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String password;
        private boolean enabled;
        private String role;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="mybooks",joinColumns = {@JoinColumn(name = "user_id")})
    private List<Book> myBooks = new ArrayList<>();

    public List<Book> getMyBooks() {
        return myBooks;
    }

    public void addBookToMyBooks(Book book) {
        this.myBooks.add(book);
    }
    public void removeBookFromMyBooks(Book book) {
        if (this.myBooks.contains(book)) {
            this.myBooks.remove(book);
        }
    }
 


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return role;
    }

    public void setRoles(String roles) {
        this.role = roles;
    }
}
