package com.example.javafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.security.cert.Extension;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

public class AddBookController {
    @FXML
    private TextField bookTitleTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button uploadButton;

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

    public void uploadButtonOnAction(ActionEvent event) {
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("PNG Files", "*.png");
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("JPG Files", "*.jpg");
        FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(ex1, ex2, ex3);

        Stage curStage = (Stage) closeButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(curStage);
        if (selectedFile != null) {
            imageTextField.setText(selectedFile.getPath());
        }

    }
    public void addBook() {
        if (errorMessageLabel.getText() != "") return;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String bookTitle = bookTitleTextField.getText();
        String category = categoryTextField.getText();
        String author = authorTextField.getText();
        String image = imageTextField.getText();
        String publishedDate = "2001-01-01";

        String insertFields = "INSERT INTO library_books(title, category, author, image, publishedDate) " +
                "VALUES('";
        String insertValues = bookTitle + "','" + category + "','" + author + "','" + image + "','" +
                publishedDate + "')";
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
