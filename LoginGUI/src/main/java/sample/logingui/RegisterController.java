package sample.logingui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private Button backButton;
    @FXML
    private Label emailMessageLabel;
    @FXML
    private Label usernameMessageLabel;
    @FXML
    private Label passwordMessageLabel;
    @FXML
    private Label confirmPasswordMessageLabel;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private VBox vboxZone;
    @FXML
    private VBox invalidInfoField;
    @FXML
    private Button testButton;
    private boolean isValidEmail;


    //public void initMessage
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //File brandingFile = new File("...");
        //Image brandingImage = new Image(brandingFile.toURL().toString());
        usernameMessageLabel.setText("jfksjdflsf");
        passwordMessageLabel.setText("Password must be at least 8 characters" +
                " with letter and number, no special or non-ASCII characters.");

//        if (invalidInfoField.getChildren() != null) {
//            invalidInfoField.getChildren().get(0).setVisible(true);
//        }

        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // If focus is lost
                if (!isValidUsername(usernameField.getText())) {
                    usernameMessageLabel.setText("Invalid username.");
                    System.out.println(usernameMessageLabel.getText());
                    if (!vboxZone.getChildren().contains(usernameMessageLabel)) {
                        vboxZone.getChildren().add(1, usernameMessageLabel);
                    }
                }
                else {
                    usernameMessageLabel.setText("");
                }
            }
            else {
                usernameMessageLabel.setText("");
            }
        });

//        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue) {  // If focus is lost
//                if (!isValidEmail(emailField.getText())) {
//                    emailMessageLabel.setText("Invalid email.");
//                }
//                else {
//                    emailMessageLabel.setText("");
//                }
//            }
//            else {
//                emailMessageLabel.setText("");
//            }
//        });

        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // If focus is lost
                if (!checkPassword()) {
                    confirmPasswordMessageLabel.setText("The passwords you entered do not match.");
                }
                else {
                    confirmPasswordMessageLabel.setText("");
                }
            }
            else {
                confirmPasswordMessageLabel.setText("");
            }
        });

    }

    public void getBackScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load(), 727, 521));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public boolean isValidUsername(String username) {
        if (username.length() < 3 || username.length() > 30) {
            return false;
        }

        String regex = "^[A-Za-z0-9_][A-Za-z0-9_-]*$";
        return username.matches(regex);
    }

    public boolean checkPassword() {
        return (enterPasswordField.getText().equals(confirmPasswordField.getText()));
    }

    public boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public boolean checkInformation() {
        boolean ok = true;
        if (!isValidUsername(usernameField.getText())) {
            invalidInfoField.getChildren().get(0).setVisible(true);
        }
        return ok;
    }

    public void usernameFieldOnAction(javafx.event.ActionEvent event) {

    }

    public void registerButtonOnAction(javafx.event.ActionEvent event) {
        if (checkInformation() == true) {
            registerMessageLabel.setText("User registered successfully!");
            registerUser();
        }
        else {
            registerMessageLabel.setText("Unsuccessfull");
        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = "";
        String lastname = "";
        String username = usernameField.getText();
        String password = enterPasswordField.getText();

        String insertFields = "INSERT INTO user_account(lastname, firstname, username, password) " +
                "VALUES('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '"
                + usernameField.getText()
                + "' AND PASSWORD = '" + enterPasswordField.getText()
                + "'";


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if (queryResult.getInt(1) == 1) {

                }
            }
            statement.executeUpdate(insertToRegister);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
