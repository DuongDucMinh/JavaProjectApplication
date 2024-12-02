package com.example.javafxapp;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import java.sql.Date;
import java.time.LocalDate;
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
    private Label currentForm_label;

    @FXML
    private AnchorPane halfNav_form;

    @FXML
    private Circle smallCircle_image;

    @FXML
    private Button halfNav_availableBtn;

    @FXML
    private Button halfNav_borrowBtn;

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
    private AnchorPane borrowBook_form;

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
    private TableColumn<AvailableBooks, Integer> col_ab_price;

    @FXML
    private TableColumn<AvailableBooks, Integer> col_ab_quantity;

    @FXML
    private Label availableBooks_title;

    @FXML
    private Label availableBooks_author;

    @FXML
    private Label availableBooks_category;

    @FXML
    private Label availableBooks_price;

    @FXML
    private Label availableBooks_quantity;

    @FXML
    private Label availableBooks_description;

    @FXML
    private Circle circle_image;

    @FXML
    private Button edit_btn;

    @FXML
    private Button borrowBooks_btn;

    @FXML
    private TableView<BorrowBooks> borrow_tableView;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowBorrowedDate;

    @FXML
    private TableColumn<BorrowBooks, Integer> col_borrowId;

    @FXML
    private TableColumn<BorrowBooks, Integer> col_borrowBookId;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowReturnDate;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowTitle;

    @FXML
    private ImageView borrow_imageView;

    @FXML
    private Button logout_btn;

    @FXML
    private Label userName_label;

    private Image image;

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
                int price = queryOutput.getInt("price");
                int quantity = queryOutput.getInt("quantity");
                String image = queryOutput.getString("image");
                Date date = queryOutput.getDate("date");
                String description = queryOutput.getString("description");

                bookSearchObservableList.add(new AvailableBooks(bookId, title, author, genre, price, quantity, image, date, description));
            }

            col_ab_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_ab_category.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_ab_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_ab_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            col_ab_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

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
                            || (AvailableBooks.getBookId() + "").contains(searchKeywords)
                            || (AvailableBooks.getQuantity() + "").contains(searchKeywords)
                            || (AvailableBooks.getPrice() + "").contains(searchKeywords);
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

    public void selectAvailableBooks() {

        AvailableBooks bookData = availableBooks_tableView.getSelectionModel().getSelectedItem();

        int num = availableBooks_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableBooks_title.setText(bookData.getTitle());
        availableBooks_author.setText(bookData.getAuthor());
        availableBooks_category.setText(bookData.getGenre());
        availableBooks_quantity.setText(bookData.getQuantity() + "");
        availableBooks_price.setText(bookData.getPrice() + "");
        availableBooks_description.setText(bookData.getDescription());

        String uri = "file:" + bookData.getImage();

        image = new Image(uri, 134, 170, false, true);

        availableBooks_imageView.setImage(image);

        GetData.bookId = bookData.getBookId();
    }

    public void sideNavButtonDesign(ActionEvent event) {

        if (event.getSource() == halfNav_availableBtn) {

            availableBooks_form.setVisible(true);
            borrowBook_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            borrowBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Available Books");

            GetData.borrowId= 0;

        } else if (event.getSource() == halfNav_borrowBtn) {

            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(true);

            borrowBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Borrowed Books");
        }

    }

    public void navButtonDesign(ActionEvent event) {

        if (event.getSource() == availableBooks_btn) {

            availableBooks_form.setVisible(true);
            borrowBook_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            borrowBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_availableBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_borrowBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Available Books");

            GetData.borrowId = 0;

        } else if (event.getSource() == borrowBooks_btn) {

            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(true);

            borrowBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            availableBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_borrowBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_availableBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Borrowed Books");

        }
    }

    public void userName() {
        userName_label.setText(GetData.username);
    }

    ObservableList<BorrowBooks> borrowBookObservableList = FXCollections.observableArrayList();

    public void showBorrowBooks() {
        borrowBookObservableList.clear();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String bookViewQuery = "SELECT b.borrow_id, b.book_id, lb.title, b.borrow_date, b.return_date, lb.image " +
                "FROM borrow_books b " +
                "JOIN library_books lb on b.book_id = lb.book_id " +
                "WHERE b.username = '"+ GetData.username + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                int borrowId = queryOutput.getInt("borrow_id");
                int bookId = queryOutput.getInt("book_id");
                String title = queryOutput.getString("title");
                String image = queryOutput.getString("image");
                Date returnDate = queryOutput.getDate("return_date");
                Date borrowDate = queryOutput.getDate("borrow_date");

                borrowBookObservableList.add(new BorrowBooks(borrowId, bookId, title, image, borrowDate, returnDate));
            }

            col_borrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
            col_borrowBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            col_borrowTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_borrowReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            col_borrowBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

            borrow_tableView.setItems(borrowBookObservableList);
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }

    public void selectBorrowBooks() {

        BorrowBooks sBook = borrow_tableView.getSelectionModel().getSelectedItem();
        int num = borrow_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        String uri = "file:" + sBook.getImage();

        image = new Image(uri, 134, 170, false, true);
        borrow_imageView.setImage(image);

        GetData.bookId = sBook.getBookId();
        GetData.borrowDate = sBook.getBorrowDate();
        GetData.returnDate = sBook.getReturnDate();
        GetData.borrowId = sBook.getBorrowId();
    }

    public void borrowBooks() {

        String checkSql = "SELECT * FROM borrow_books WHERE username = '" + GetData.username + "' "
                + "AND book_id = " + GetData.bookId;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getDBConnection();

        try {
            PreparedStatement preparedStatement = connect.prepareStatement(checkSql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Alert alert;

            if (availableBooks_title.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the book");
                alert.showAndWait();

            } else if (resultSet.next()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("You borrowed this book!");
                alert.showAndWait();

            } else {
                LocalDate localDate = LocalDate.now();  // Lấy ngày hiện tại
                Date sqlDate = Date.valueOf(localDate); // Chuyển thành java.sql.Date
                GetData.borrowDate = sqlDate;

                String sql = "INSERT INTO borrow_books VALUES (?,?,?,?,?)";
                PreparedStatement prepare = connect.prepareStatement(sql);
                prepare.setInt(1, GetData.borrowId);
                prepare.setString(2, GetData.username);
                prepare.setInt(3, GetData.bookId);
                prepare.setDate(4, GetData.borrowDate);
                prepare.setDate(5, GetData.returnDate);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Borrowed.");
                alert.showAndWait();

                showBorrowBooks();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void returnBooks() {

        String sql = "DELETE FROM borrow_books WHERE borrow_id = '" + GetData.borrowId + "'";

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connect = connectNow.getDBConnection();

        try {

            Alert alert;

            if (borrow_imageView.getImage() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select the book you want to Return");
                alert.showAndWait();

            } else {

                Statement statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Return.");
                alert.showAndWait();

                showBorrowBooks();

                borrow_imageView.setImage(null);
            }
        } catch (Exception e) {
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
        userName();
        showBorrowBooks();
    }
}
