package com.example.javafxapp;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("images/_@nethbookpoint_com__1_.png");
        Image brandingImage = new Image(file.toURI().toString());
        brandingImageView.setImage(brandingImage);
    }

    public void loginButtonOnAction() throws IOException {
        if (!usernameField.getText().isBlank()
                && !enterPasswordField.getText().isBlank()) {
            validateLogin();

            if (loginMessageLabel.getText().equals("Congratulations!")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
                Stage dashboardStage = (Stage) loginButton.getScene().getWindow();
                dashboardStage.setScene(new Scene(fxmlLoader.load(), 986, 600));
                dashboardStage.show();
            }
        }
        else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '"
                + usernameField.getText()
                + "' AND PASSWORD = '" + enterPasswordField.getText()
                + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Congratulations!");
                }
                else {
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void registerButtonOnAction(ActionEvent event) {
        createAccountForm();
    }

    public void createAccountForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Stage registerStage = (Stage) registerButton.getScene().getWindow();
            registerStage.setScene(new Scene(fxmlLoader.load(), 737, 521));
            registerStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}