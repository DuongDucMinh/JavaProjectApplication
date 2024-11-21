package com.example.javafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class AddBookController {
    @FXML
    private TextField bookTitleTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField publisherTextField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button saveButton;

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
    }

    public void cancelOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }
    public void addBook() {
        if (errorMessageLabel.getText() != "") return;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String bookTitle = bookTitleTextField.getText();
        String category = categoryTextField.getText();
        String author = authorTextField.getText();
        String image = "https:";

        String insertFields = "INSERT INTO library_books(title, category, author, image) " +
                "VALUES('";
        String insertValues = bookTitle + "','" + category + "','" + author + "','" + image + "')";
        String insertToBooks = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToBooks);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
