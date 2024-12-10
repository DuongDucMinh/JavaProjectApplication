package com.example.javafxapp;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
public class UserSearchController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private TextField keywordTextField;
    @FXML
    private TableView<UserSearchModel> userTableView;
    @FXML
    private TableColumn<UserSearchModel, String> firstnameTableColumn;
    @FXML
    private TableColumn<UserSearchModel, String> lastnameTableColumn;
    @FXML
    private TableColumn<UserSearchModel, String> usernameTableColumn;
    @FXML
    private TableColumn<UserSearchModel, Integer> userIDTableColumn;
    @FXML
    private TableColumn<UserSearchModel, Void> actionTableColumn;

    ObservableList<UserSearchModel> userSearchModelObservableList = FXCollections.observableArrayList();

    public void actionColumnOnAction() {
        actionTableColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button();

            {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                // Button action

                button.setOnAction(event -> {
                    deleteAccountOnAction(getTableView().getItems().get(getIndex()));
                    System.out.println("OK");
                });

                button.sceneProperty().addListener((observable, oldScene, newScene) -> {
                    if (newScene != null) {
                        button.setGraphic(icon);
                        button.getScene().getStylesheets().add(
                                Objects.requireNonNull(getClass().getResource("/com/example/javafxapp/dashboardDesign.css")).toExternalForm()
                        );

                        button.getStyleClass().add("close-btn"); // Add the CSS class
                    }
                });

                userTableView.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
                    // Refresh the table to ensure the button only shows for the selected row
                    userTableView.refresh();
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                // Show the button only for the selected row
                if (empty || getTableView().getSelectionModel().getSelectedIndex() != getIndex()) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }
    public void deleteAccountOnAction(UserSearchModel user) {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String deleteFields = "DELETE FROM user_account WHERE account_id =  ";
        String deleteValue = "'" + user.getUserID() + "'";
        String deleteBook = deleteFields + deleteValue;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteBook);

            showUsers();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
    }

    public void addAccountMenuItemOnAction(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addAccount.fxml"));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 452, 660));
        dashboardStage.show();

        AddAccountController addBookController = fxmlLoader.getController();
        addBookController.setParentController(this);
    }

    public void showUsers() {
        userSearchModelObservableList.clear();
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();
        String userViewQuery = "SELECT account_id, firstname, lastname, username FROM user_account";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userViewQuery);
            while (queryOutput.next()) {
                int userID = queryOutput.getInt("account_id");
                String firstName = queryOutput.getString("firstname");
                String lastName = queryOutput.getString("lastname");
                String username = queryOutput.getString("username");
                userSearchModelObservableList.add(new UserSearchModel(userID, firstName, lastName, username));
            }
            userIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            userTableView.setItems(userSearchModelObservableList);
            // Danh sach loc ban dau
            FilteredList<UserSearchModel> filteredData = new FilteredList<>(userSearchModelObservableList, b -> true);
            // Thêm một ChangeListener vào thuộc tính textProperty() của keywordTextField => lắng nghe sự thay đổi của văn bản trong TextField.
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Cập nhật điều kiện lọc (Predicate) của filteredData mỗi khi văn bản trong TextField thay đổi.
                filteredData.setPredicate(userSearchModel -> {
                    if (newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }
                    String searchKeywords = newValue.toLowerCase();
                    // no match found
                    return userSearchModel.getFirstName().toLowerCase().contains(searchKeywords)
                            || userSearchModel.getLastName().toLowerCase().contains(searchKeywords)
                            || (userSearchModel.getUserID() + "").contains(searchKeywords)
                            || userSearchModel.getUsername().toLowerCase().contains(searchKeywords);
                });
            });
            SortedList<UserSearchModel> sortedData = new SortedList<>(filteredData);
            // Liên kết kết quả được sắp xếp với Chế độ xem bảng
            sortedData.comparatorProperty().bind(userTableView.comparatorProperty());
            // Apply filtered and sorted data to the Table View
            actionColumnOnAction();
            userTableView.setItems(sortedData);
            userTableView.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
                // Refresh the table to ensure the button only shows for the selected row
                userTableView.refresh();
            });
        } catch (SQLException e) {
            Logger.getLogger(UserSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}