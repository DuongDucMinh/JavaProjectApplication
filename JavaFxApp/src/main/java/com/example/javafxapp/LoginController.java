package com.example.javafxapp;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

    private boolean logged_in = false;
    private boolean isUser = true;

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

            if (logged_in) {
                if (isUser) {
                    GetData.username = usernameField.getText();
                    //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard1.fxml"));
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboardUser.fxml"));
                    Stage dashboardStage = (Stage) loginButton.getScene().getWindow();
                    dashboardStage.setScene(new Scene(fxmlLoader.load(), 1233, 704));
                    dashboardStage.show();
                }
                else {
                    GetData.username = usernameField.getText();
                    //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard1.fxml"));
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard1.fxml"));
                    Stage dashboardStage = (Stage) loginButton.getScene().getWindow();
                    dashboardStage.setScene(new Scene(fxmlLoader.load(), 1233, 704));
                    dashboardStage.show();
                }
            }
        }
        else {
            loginMessageLabel.setText("Please enter username and password");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String verifyType = "SELECT count(1) FROM user_account WHERE type = 'manager' AND username = '"
                + usernameField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyType);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    System.out.print("admin");
                    isUser = false;
                }
                else {
                    System.out.print("not admin");
                    isUser = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '"
                + usernameField.getText()
                + "' AND PASSWORD = '" + enterPasswordField.getText()
                + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    //loginMessageLabel.setText("Congratulations!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + usernameField.getText() + "!");
                    alert.showAndWait();
                    logged_in = true;
                }
                else {
                    //loginMessageLabel.setText("Invalid login. Please try again");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid Credentials");
                    alert.setContentText("Please check your username and password.");
                    alert.showAndWait();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void registerButtonOnAction(ActionEvent event) {
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