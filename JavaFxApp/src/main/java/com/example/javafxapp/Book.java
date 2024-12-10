package com.example.javafxapp;

public class Book {
    private String title;
    private String author;
    private String imageUrl;
    private String description;
    private int publishedDate;
    private int pageCount;
    private String publisher;
    public String category;

    public Book(String title, String author, String imageUrl, String description, int publishedDate, int pageCount, String publisher, String category) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.category = category;
    }

    public Book(String title, String author, String imageUrl, String description) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(String title, String author, String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedDate() {
        return Integer.toString(publishedDate);
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

}
