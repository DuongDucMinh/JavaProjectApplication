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
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookSearchController implements Initializable {
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<BookSearchModel> bookTableView;

    @FXML
    private TableColumn<BookSearchModel, String> titleTableColumn;

    @FXML
    private TableColumn<BookSearchModel, String> authorTableColumn;

    @FXML
    private TableColumn<BookSearchModel, String> categoryTableColumn;

    @FXML
    private TableColumn<BookSearchModel, Date> publishedDateTableColumn;

    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;
    @FXML
    private TableColumn<BookSearchModel, Void> actionTableColumn;
    @FXML
    private TableColumn<BookSearchModel, Integer> bookIDTableColumn;

    ObservableList<BookSearchModel> BookSearchModelObservableList = FXCollections.observableArrayList();

    public void closeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.close();
    }

    public void minimizeButtonOnAction(ActionEvent event) {
        Stage curStage = (Stage) closeButton.getScene().getWindow();
        curStage.setIconified(true);
    }

    public void deleteBookOnAction(BookSearchModel book) {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String deleteFields = "DELETE FROM library_books WHERE book_id =  ";
        String deleteValue = "'" + book.getBookID() + "'";
        String deleteBook = deleteFields + deleteValue;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deleteBook);

            showBooks();
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
        showBooks();
    }

    public void showBooks() {
        BookSearchModelObservableList.clear();
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
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

                BookSearchModelObservableList.add(new BookSearchModel(bookID, title, author, category, publishedDate));
            }

            bookIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            publishedDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

            bookTableView.setItems(BookSearchModelObservableList);

            // Danh sach loc ban dau
            FilteredList<BookSearchModel> filteredData = new FilteredList<>(BookSearchModelObservableList, b -> true);

            // Thêm một ChangeListener vào thuộc tính textProperty() của keywordTextField => lắng nghe sự thay đổi của văn bản trong TextField.
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Cập nhật điều kiện lọc (Predicate) của filteredData mỗi khi văn bản trong TextField thay đổi.
                filteredData.setPredicate(BookSearchModel -> {

                    if (newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String searchKeywords = newValue.toLowerCase();
                    // no match found
                    return BookSearchModel.getTitle().toLowerCase().contains(searchKeywords)
                            || BookSearchModel.getCategory().toLowerCase().contains(searchKeywords)
                            || (BookSearchModel.getBookID() + "").contains(searchKeywords)
                            || BookSearchModel.getAuthor().toLowerCase().contains(searchKeywords)
                            || BookSearchModel.getPublishedDate().toString().toLowerCase().contains(searchKeywords);
                });
            });

            SortedList<BookSearchModel> sortedData = new SortedList<>(filteredData);

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
            Logger.getLogger(BookSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void addBookMenuItemOnAction(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addBook.fxml"));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 452, 509));
        dashboardStage.show();
        AddBookController addBookController = fxmlLoader.getController();
        addBookController.setParentController(this);
    }
}