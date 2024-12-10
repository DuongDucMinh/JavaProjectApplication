package com.example.javafxapp;

import java.util.Date;

public class AvailableBooks {
    private final int bookId;
    private final String title;
    private final String author;
    private final String genre;
    private final String image;
    private final Date date;
    private final Integer quantity;
    private final String description;

    public AvailableBooks(Integer bookId, String title, String author, String genre, Integer quantity, String image, Date date, String description) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
        this.image = image;
        this.date = date;
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getDescription() {
        return description;
    }
    public int getBookId(){
        return bookId;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getGenre(){
        return genre;
    }
    public String getImage(){
        return image;
    }
    public Date getDate(){
        return date;
    }
}