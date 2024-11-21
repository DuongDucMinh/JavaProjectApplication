package com.example.javafxapp;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane halfNav_form;

    @FXML
    private Circle smallCircle_image;

    @FXML
    private Button halfNav_availableBtn;

    @FXML
    private Button halfNav_takeBtn;

    @FXML
    private Button halfNav_returnBtn;

    @FXML
    private Button halfNav_saveBtn;

    @FXML
    private AnchorPane mainCenter_form;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private Button bars_btn;

    @FXML
    private Button arrow_btn;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Button availableBooks_btn;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<AvailableBooks> availableBooks_tableView;

    @FXML
    private TableColumn<AvailableBooks, Integer> col_ab_bookID;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_Author;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_bookTitle;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_category;

    @FXML
    private TableColumn<AvailableBooks, String> col_ab_publishedDate;

    @FXML
    private Label availableBooks_title;

    @FXML
    private Circle circle_image;

    @FXML
    private Button edit_btn;

    @FXML
    private Button issueBooks_btn;

    @FXML
    private Button returnBooks_btn;

    @FXML
    private Button save_btn;

    @FXML
    private Button savedBooks_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button take_btn;

    @FXML
    private Label userName_label;

    private Image image;

    /*private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;*/

    private String comboBox[] = {"Male", "Female", "Others"};

    private double x = 0;
    private double y = 0;

    ObservableList<AvailableBooks> bookSearchObservableList = FXCollections.observableArrayList();

    public void showAvailableBooks() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String bookViewQuery = "SELECT * FROM library_books";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                int bookId = queryOutput.getInt("book_id");
                String title = queryOutput.getString("title");
                String genre = queryOutput.getString("category");
                String author = queryOutput.getString("author");
                String image = queryOutput.getString("image");
                Date date = queryOutput.getDate("date");

                bookSearchObservableList.add(new AvailableBooks(bookId, title, author, genre, image, date));
            }

            col_ab_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_ab_category.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_ab_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            availableBooks_tableView.setItems(bookSearchObservableList);

            // Danh sach loc ban dau
            FilteredList<AvailableBooks> filteredData = new FilteredList<>(bookSearchObservableList, b -> true);

            // Thêm một ChangeListener vào thuộc tính textProperty() của keywordTextField => lắng nghe sự thay đổi của văn bản trong TextField.
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Cập nhật điều kiện lọc (Predicate) của filteredData mỗi khi văn bản trong TextField thay đổi.
                filteredData.setPredicate(AvailableBooks -> {

                    if (newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String searchKeywords = newValue.toLowerCase();
                    // no match found
                    return AvailableBooks.getTitle().toLowerCase().contains(searchKeywords)
                            || AvailableBooks.getAuthor().toLowerCase().contains(searchKeywords)
                            || AvailableBooks.getGenre().toLowerCase().contains(searchKeywords)
                            || AvailableBooks.getDate().toString().contains(searchKeywords)
                            || (AvailableBooks.getBookId() + "").contains(searchKeywords);
                });
            });

            SortedList<AvailableBooks> sortedData = new SortedList<>(filteredData);

            // Liên kết kết quả được sắp xếp với Chế độ xem bảng
            sortedData.comparatorProperty().bind(availableBooks_tableView.comparatorProperty());

            // Apply filtered and sorted data to the Table View
            availableBooks_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void sliderArrow() {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(-224);

        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(-224 + 90);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(false);
            bars_btn.setVisible(true);

        });

        slide2.play();
        slide1.play();
        slide.play();

    }

    public void sliderBars() {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(nav_form);
        slide.setToX(0);

        TranslateTransition slide1 = new TranslateTransition();
        slide1.setDuration(Duration.seconds(.5));
        slide1.setNode(mainCenter_form);
        slide1.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(halfNav_form);
        slide2.setToX(-77);

        slide.setOnFinished((ActionEvent event) -> {

            arrow_btn.setVisible(true);
            bars_btn.setVisible(false);

        });

        slide2.play();
        slide1.play();
        slide.play();
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logout_btn) {
                // TO SWAP FROM DASHBOARD TO LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                logout_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvailableBooks();
    }
}
