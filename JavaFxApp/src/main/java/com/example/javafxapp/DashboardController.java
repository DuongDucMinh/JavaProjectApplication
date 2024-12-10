package com.example.javafxapp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.imageio.ImageIO;

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
    private TableView<AvailableBooks> availableBooks_tableView;

    @FXML
    private Label availableBooks_title;

    @FXML
    private Circle circle_image;

    @FXML
    private TableColumn<?, ?> col_ab_Author;

    @FXML
    private TableColumn<?, ?> col_ab_bookTitle;

    @FXML
    private TableColumn<?, ?> col_ab_category;

    @FXML
    private TableColumn<?, ?> col_ab_publishedDate;

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
    @FXML
    private MenuItem addBookMenuItem;

    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private String comboBox[] = {"Male", "Female", "Others"};

    private double x = 0;
    private double y = 0;

    @FXML
    private Label currentForm_label;

    @FXML
    private Button halfNav_borrowBtn;

    @FXML
    private AnchorPane borrowBook_form;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableColumn<AvailableBooks, Integer> col_ab_bookID;

    @FXML
    private TableColumn<AvailableBooks, Integer> col_ab_quantity;

    @FXML
    private Label availableBooks_author;

    @FXML
    private Label availableBooks_category;

    @FXML
    private Label availableBooks_;

    @FXML
    private Label availableBooks_quantity;

    @FXML
    private Label availableBooks_description;

    @FXML
    private Button borrowBooks_btn;

    @FXML
    private TableView<BorrowBooks> borrow_tableView;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowBorrowedDate;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowUserName;

    @FXML
    private TableColumn<BorrowBooks, Integer> col_borrowId;

    @FXML
    private TableColumn<BorrowBooks, Integer> col_borrowBookId;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowReturnDate;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowTitle;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowStatus;

    @FXML
    private ImageView borrow_imageView;

    ObservableList<AvailableBooks> bookSearchObservableList = FXCollections.observableArrayList();

    public void showAvailableBooks() {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        bookSearchObservableList.clear();

        String bookViewQuery = "SELECT * FROM library_books";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                int bookId = queryOutput.getInt("book_id");
                String title = queryOutput.getString("title");
                String genre = queryOutput.getString("category");
                String author = queryOutput.getString("author");
                int quantity = queryOutput.getInt("quantity");
                String image = queryOutput.getString("image");
                Date date = queryOutput.getDate("publishedDate");
                String description = queryOutput.getString("description");

                bookSearchObservableList.add(new AvailableBooks(bookId, title, author, genre, quantity, image, date, description));
            }

            col_ab_bookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            col_ab_bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_ab_category.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_ab_Author.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_ab_publishedDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            //col_ab_.setCellValueFactory(new PropertyValueFactory<>(""));
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
                            || (AvailableBooks.getQuantity() + "").contains(searchKeywords);
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
        availableBooks_description.setText(bookData.getDescription());
        System.out.println(bookData.getImage());
        availableBooks_imageView.setImage(new Image(bookData.getImage(), true));

        //String uri = "file:" + bookData.getImage();

        //image = new Image(uri, 134, 170, false, true);

        //availableBooks_imageView.setImage(image);

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

    @FXML
    private ImageView avatarView;
    @FXML
    private Button uploadButton;

    public void uploadButtonOnAction() {
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("PNG Files", "*.png");
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("JPG Files", "*.jpg");
        FileChooser.ExtensionFilter ex3 = new FileChooser.ExtensionFilter("All Files", "*.*");
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(ex1, ex2, ex3);

        Stage curStage = (Stage) uploadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(curStage);
        if (selectedFile != null) {
            String uri = "file:" + selectedFile.getPath();

            image = new Image(uri, 134, 170, false, true);

            avatarView.setImage(image);
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

    public void refreshButtonOnAction() {
        showAvailableBooks();
    }

    public void userName() {
        userName_label.setText(GetData.username);
    }

    ObservableList<BorrowBooks> borrowBookObservableList = FXCollections.observableArrayList();

    public void showBorrowBooks() {
        borrowBookObservableList.clear();

        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        LocalDate localDate = LocalDate.now();  // Lấy ngày hiện tại
        java.sql.Date sqlDate = Date.valueOf(localDate); // Chuyển thành java.sql.Date

        String bookViewQuery = "SELECT b.*, l.*, IF(b.return_date >= '" + sqlDate + "', 'pending', 'overdue') AS status " +
                "FROM borrow_books b " +
                "JOIN library_books l ON l.title = b.title";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                int borrowId = queryOutput.getInt("borrow_id");
                String userName = queryOutput.getString("username");
                String title = queryOutput.getString("b.title");
                Date returnDate = queryOutput.getDate("return_date");
                Date borrowDate = queryOutput.getDate("borrow_date");
                String image = queryOutput.getString("image");
                String status = queryOutput.getString("status");

                borrowBookObservableList.add(new BorrowBooks(borrowId, userName, title, borrowDate, returnDate, image, status));
            }

            col_borrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
            col_borrowUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            col_borrowTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_borrowReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            col_borrowBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            col_borrowStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            borrow_tableView.setItems(borrowBookObservableList);
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvailableBooks();
        userName();
        showBorrowBooks();
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

    public void exit() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void addBookMenuItemOnAction(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addBook.fxml"));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 452, 594));
        dashboardStage.show();
    }

    public void deleteBookMenuItemOnAction(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("deleteBook.fxml"));
        //System.out.print((fxmlLoader.getController() == null));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 845, 705));
        dashboardStage.show();
    }

    public void addAccountMenuItemOnAction(ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addAccount.fxml"));
        //System.out.print((fxmlLoader.getController() == null));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 452, 660));
        dashboardStage.show();
    }

    public void manageAccountButtonOnAction(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userManagement.fxml"));
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 845, 705));
        dashboardStage.show();
    }

//    public void addBookFromAPIOnAction(ActionEvent event) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("managerbookApiSearch.fxml"));
//
//        Stage dashboardStage = new Stage();
//        dashboardStage.setScene(new Scene(fxmlLoader.load(), 1138, 756));
//
//        // Get the controller and set the parent
//        ManagerBookController managerBookController = fxmlLoader.getController();
//        managerBookController.setParentController(this);
//
//        dashboardStage.show();
//    }

    public void addBookFromAPIOnAction(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("managerbookApiSearch.fxml"));

        // Set the parent controller statically
        ManagerBookController.setParentController(this);

        Stage dashboardStage = new Stage();
        dashboardStage.setScene(new Scene(fxmlLoader.load(), 1138, 756));
        dashboardStage.show();
    }


}
