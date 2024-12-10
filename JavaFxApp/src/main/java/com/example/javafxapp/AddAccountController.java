package com.example.javafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.PriorityQueue;
import java.util.ResourceBundle;


public class AddAccountController implements Initializable {
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button uploadButton;
    @FXML
    private TextField imageTextField;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private Label errorMessageLabel;

    private UserSearchController parentController;

    private PriorityQueue<Integer> pq = new PriorityQueue<>();

    public String[] errorMessage = new String[3];
    private boolean isValidEmail;

    public void setParentController(UserSearchController parentController) {
        this.parentController = parentController;
    }

    public boolean isValidUsername(String username) {
        if (username.length() < 3 || username.length() > 30) {
            return false;
        }

        String regex = "^[A-Za-z0-9_][A-Za-z0-9_-]*$";
        return username.matches(regex);
    }

    public boolean isValidPassword(String password) {
        // Regular expression to check password:
        // - At least 8 characters long
        // - Contains letters and numbers only (no special characters)
        // - Must contain at least one letter and one number
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";

        return password.matches(regex);
    }

    public void initMessage() {
        errorMessage[0] = "Invalid Username or Email.";
        errorMessage[1] = "Password must be at least 8 characters"
                + "with letter and number, no special or non-ASCII characters.";
        errorMessage[2] = "The passwords you entered do not match.";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //File brandingFile = new File("...");
        //Image brandingImage = new Image(brandingFile.toURL().toString());

//        if (invalidInfoField.getChildren() != null) {
//            invalidInfoField.getChildren().get(0).setVisible(true);
//        }

        initMessage();
        usernameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // If focus is lost
                if (!isValidUsername(usernameTextField.getText())) {
                    if (!pq.contains(0)) {
                        pq.add(0);
                        //System.out.print(pq.size());
                    }
                }
                else {
                    pq.remove(0);
                }

                if (pq.size() > 0)
                    errorMessageLabel.setText(errorMessage[pq.peek()]);
                else
                    errorMessageLabel.setText("");
            }
        });

        enterPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // If focus is lost
                if (!isValidPassword(enterPasswordField.getText())) {
                    if (!pq.contains(1))
                        pq.add(1);
                }
                else {
                    pq.remove(1);
                }

                if (pq.size() > 0)
                    errorMessageLabel.setText(errorMessage[pq.peek()]);
                else
                    errorMessageLabel.setText("");
            }
        });

    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void cancelOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
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

    public void registerButtonOnAction(ActionEvent event) {
        if (errorMessageLabel.getText().equals("") == true) {

            registerUser();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Account Successful");
            alert.setHeaderText(null);
            alert.setContentText("Welcome, " + usernameTextField.getText() + "!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Account Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please check your username and password.");
            alert.showAndWait();
        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = enterPasswordField.getText();

        String insertFields = "INSERT INTO user_account(lastname, firstname, username, password) " +
                "VALUES('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '"
                + usernameTextField.getText()
                + "' AND PASSWORD = '" + enterPasswordField.getText()
                + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    errorMessageLabel.setText("Duplicate Username!");
                }
            }

            if (errorMessageLabel.getText().equals("")) {
                statement.executeUpdate(insertToRegister);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        parentController.showUsers();
    }
}
