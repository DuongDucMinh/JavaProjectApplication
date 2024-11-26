package com.example.javafxapp;
import java.sql.Date;
public class BorrowBooks {
    private final int borrowId;
    private final int bookId;
    private final String title;
    private final Date returnDate;
    private final Date borrowDate;
    private final String image;

    public BorrowBooks(Integer borrowId, Integer bookId, String title, String image, Date borrowDate, Date returnDate) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.title = title;
        this.returnDate = returnDate;
        this.image = image;
        this.borrowDate = borrowDate;
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
    public String getImage(){
        return image;
    }
    public Date getReturnDate(){
        return returnDate;
    }
}
