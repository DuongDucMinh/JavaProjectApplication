package com.example.javafxapp;

import java.util.Date;

public class BookSearchModel {
    int bookID;
    String title;
    String category;
    String author;
    Date publishedDate;

    public BookSearchModel(int bookID, String title, String author, String category, Date publishedDate) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publishedDate = publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
