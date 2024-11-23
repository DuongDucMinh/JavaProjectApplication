package com.example.javafxapp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSearchController implements Initializable {
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<UserSearchModel> bookTableView;

    @FXML
    private TableColumn<UserSearchModel, String> titleTableColumn;

    @FXML
    private TableColumn<UserSearchModel, String> authorTableColumn;

    @FXML
    private TableColumn<UserSearchModel, String> categoryTableColumn;

    @FXML
    private TableColumn<UserSearchModel, Date> publishedDateTableColumn;

    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private TableColumn<UserSearchModel, Void> actionTableColumn;
    @FXML
    private TableColumn<UserSearchModel, Integer> bookIDTableColumn;

    ObservableList<UserSearchModel> userSearchModelObservableList = FXCollections.observableArrayList();

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
    }

    public void deleteBookOnAction(UserSearchModel book) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String deleteFields = "DELETE FROM library_books WHERE book_id =  ";
        String deleteValue = "'" + book.getBookID() + "'";
        String deleteBook = deleteFields + deleteValue;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteBook);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void actionColumnOnAction() {
        actionTableColumn.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button();

            {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                // Button action

                button.setOnAction(event -> {
                    deleteBookOnAction(getTableView().getItems().get(getIndex()));
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

                bookTableView.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
                    // Refresh the table to ensure the button only shows for the selected row
                    bookTableView.refresh();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String userViewQuery = "SELECT book_id, title, author, category, publishedDate FROM library_books";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userViewQuery);

            while (queryOutput.next()) {
                int bookID = queryOutput.getInt("book_id");
                String title = queryOutput.getString("title");
                String category = queryOutput.getString("category");
                String author = queryOutput.getString("author");
                Date publishedDate = queryOutput.getDate("publishedDate");

                userSearchModelObservableList.add(new UserSearchModel(bookID, title, author, category, publishedDate));
            }

            bookIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            publishedDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

            bookTableView.setItems(userSearchModelObservableList);

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
                    return userSearchModel.getTitle().toLowerCase().contains(searchKeywords)
                            || userSearchModel.getCategory().toLowerCase().contains(searchKeywords)
                            || (userSearchModel.getBookID() + "").contains(searchKeywords)
                            || userSearchModel.getAuthor().toLowerCase().contains(searchKeywords)
                            || userSearchModel.getPublishedDate().toString().toLowerCase().contains(searchKeywords);
                });
            });

            SortedList<UserSearchModel> sortedData = new SortedList<>(filteredData);

            // Liên kết kết quả được sắp xếp với Chế độ xem bảng
            sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());

            actionColumnOnAction();
            // Apply filtered and sorted data to the Table View
            bookTableView.setItems(sortedData);

            bookTableView.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
                // Refresh the table to ensure the button only shows for the selected row
                bookTableView.refresh();
            });

        } catch (SQLException e) {
            Logger.getLogger(UserSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}