package com.example.javafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.io.File;
import java.security.cert.Extension;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

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
    @FXML
    private TextField quantityTextField;

    private BookSearchController parentController;

    public void setParentController(BookSearchController parentController) {
        this.parentController = parentController;
    }

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
    public void addBook() {
        if (errorMessageLabel.getText() != "") return;
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String bookTitle = bookTitleTextField.getText();
        String category = categoryTextField.getText();
        String author = authorTextField.getText();
        String image = imageTextField.getText();
        String publishedDate = getRandomPublishedDate(1900, 2005);
        String quantity = quantityTextField.getText();
        String description = "aaaaa";

        String insertFields = "INSERT INTO library_books(title, category, author, image, publishedDate, quantity, description) " +
                "VALUES('";
        String insertValues = bookTitle + "','" + category + "','" + author + "','" + image + "','" +
                publishedDate + "','" + quantity + "','" + description + "')";
        String insertToBooks = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToBooks);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library Book");
            alert.setHeaderText(null);
            alert.setContentText("Add Book Successful");
            alert.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        parentController.showBooks();
    }


}
