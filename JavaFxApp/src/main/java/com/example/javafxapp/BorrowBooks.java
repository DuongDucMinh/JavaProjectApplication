package com.example.javafxapp;

import java.sql.Date;
public class BorrowBooks {
    private final int borrowId;
    private final String userName;
    private final int bookId;
    private final String title;
    private final Date returnDate;
    private final Date borrowDate;
    private final String image;
    private final String status;

    public BorrowBooks(Integer borrowId, String userName, String title, Date borrowDate, Date returnDate, String image, String status) {
        this.userName = userName;
        this.borrowId = borrowId;
        this.title = title;
        this.returnDate = returnDate;
        this.borrowDate = borrowDate;
        this.image = image;
        this.status = status;
        bookId = 0;
    }

    public BorrowBooks(Integer borrowId, Integer bookId, String title, Date borrowDate, Date returnDate, String image, String status) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.title = title;
        this.returnDate = returnDate;
        this.borrowDate = borrowDate;
        this.image = image;
        this.status = status;
        userName = "";
    }
    public String getUserName() {
        return userName;
    }
    public int getBorrowId() {
        return borrowId;
    }
    public int getBookId() {
        return bookId;
    }
    public String getTitle(){
        return title;
    }
    public Date getBorrowDate(){
        return borrowDate;
    }
    public Date getReturnDate(){
        return returnDate;
    }
    public String getImage() {
        return image;
    }
    public String getStatus() {
        return status;
    }
}