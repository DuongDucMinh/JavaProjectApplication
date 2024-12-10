package com.example.javafxapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static DatabaseConnection instance; // Instance duy nhất của class
    private Connection databaseLink;

    // Constructor private để ngăn tạo đối tượng trực tiếp
    private DatabaseConnection() {
        // Cấu hình cơ sở dữ liệu
        String databaseName = "demo_db";
        String databaseUser = "root";
        String databasePassword = "!@#Thinh2005#@!";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            // Nạp driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức để lấy instance duy nhất của class
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) { // Kiểm tra lại trong block synchronized
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Phương thức để lấy kết nối cơ sở dữ liệu
    public Connection getDBConnection() {
        return databaseLink;
    }
}
