package com.example.javafxapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getDBConnection() {

        //String databaseName = "classicmodels";
        String databaseName = "demo_db";
        String databaseUser = "root";
        String databasePassword = "Minhnhinho3110";
        String url = "jdbc:mysql://localhost:3307/" + databaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }
}
