package com.task2.task2.Entities;

import java.util.Date;

public class BorrowedBookDTO {
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private Date transactionDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BorrowedBookDTO(Long bookId,String title, String author, String genre, Date transactionDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.transactionDate = transactionDate;
        this.bookId = bookId;
    }
}
