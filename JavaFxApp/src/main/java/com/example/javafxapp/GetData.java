package com.example.javafxapp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GetData {
    public static String path;
    public static String username;


    public static String getRandomPublishedDate(int startYear, int endYear) {
        Random random = new Random();

        // Generate a random year within the given range
        int year = random.nextInt(endYear - startYear + 1) + startYear;

        // Generate a random month (1 to 12)
        int month = random.nextInt(12) + 1;

        // Generate a random day based on the month and year
        int day = random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth()) + 1;

        // Format the date as a string
        LocalDate randomDate = LocalDate.of(year, month, day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return randomDate.format(formatter);
    }

    // SAVED DATA
    public static Book book;
    public static int borrowId;
    public static int bookId;
    public static Date returnDate;
    public static Date borrowDate;

    public static String title;
    public static String author;
    public static String category;
    public static String description;
    public static String pathImage;
}