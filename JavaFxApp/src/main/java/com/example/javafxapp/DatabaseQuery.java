package com.example.javafxapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseQuery {
    public static void generateBookHtmlFiles() {
        String url = "jdbc:sqlite:library.db";
        String query = "SELECT id, title, author, year, summary FROM books";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String summary = rs.getString("summary");

                //HtmlGenerator.generateHtml(id, title, author, year, summary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateQRCodes(String baseUrl) {
        String url = "jdbc:sqlite:library.db";
        String query = "SELECT id FROM books";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                //QRCodeGenerator.generateQRCode(id, 300, 300);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
