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
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;

public class UserDashboardController implements Initializable {
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
    public AnchorPane availableBooks_form;

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
    @FXML
    private Button uploadButton;

    private Image image;

    private double x = 0;
    private double y = 0;

    @FXML
    private Label currentForm_label;

    @FXML
    private Button halfNav_borrowBtn;

    @FXML
    public AnchorPane borrowBook_form;

    @FXML
    public AnchorPane availableBooks1_form;

    @FXML
    private TextField keywordTextField;

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
    private Button availableBooks_btn1;

    @FXML
    private Button halfNav_availableBtn1;

    @FXML
    private Button halfNav_borrowBtn1;

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
    private TableColumn<BorrowBooks, String> col_borrowStatus;

    @FXML
    private TableColumn<BorrowBooks, String> col_borrowTitle;

    @FXML
    private ImageView borrow_imageView;

    @FXML
    private ImageView user_image;


    @FXML
    private TilePane bookContainer1;
    ObservableList<AvailableBooks> bookSearchObservableList = FXCollections.observableArrayList();


    private void showBookDetails(Book book) throws IOException {

        Stage detailStage = new Stage();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("bookDetails.fxml"));
        Parent root = loader1.load();
        Map<String, Object> namespace = loader1.getNamespace();

        ImageView imageView = (ImageView) namespace.get("imageView");
        imageView.setImage(new Image(book.getImageUrl(), true));

        Label titleLabel = (Label) namespace.get("titleLabel");
        titleLabel.setText(book.getTitle());

        Label authorLabel = (Label) namespace.get("authorLabel");
        authorLabel.setText(book.getAuthor());

        Label descriptionLabel = (Label) namespace.get("descriptionLabel");
        descriptionLabel.setText(book.getDescription());

        Label numberOfPages = (Label) namespace.get("pagesLabel");
        numberOfPages.setText(String.valueOf(book.getPageCount()));

        Label publishedDate = (Label) namespace.get("publishedDate");
        publishedDate.setText(String.valueOf(book.getPublishedDate()));

        Label publisher = (Label) namespace.get("publisherLabel");
        publisher.setText(book.getPublisher());

        if (availableBooks_form.isVisible()) {
            Button borrowButton = (Button) namespace.get("borrow_btn");
            borrowButton.setVisible(false);
        }
        GetData.book = book;

        HBox relatedBookContainer = (HBox) namespace.get("relatedBookContainer");
        List<Book> relatedBooks = relatedBookList(book);
        for (Book book1 : relatedBooks) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookItem.fxml"));
            VBox bookItem = loader.load();

            ImageView bookImage = (ImageView) bookItem.lookup("#bookImage");
            Label titleLabel1 = (Label) bookItem.lookup("#titleLabel");
            Label authorLabel1 = (Label) bookItem.lookup("#authorLabel");

            // Set book details
            bookImage.setImage(new Image(book1.getImageUrl(), true));
            titleLabel1.setText(book1.getTitle());
            authorLabel1.setText(book1.getAuthor());

            bookItem.setOnMouseClicked(event -> {
                // Handle click event
                try {
                    showBookDetails(book1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            relatedBookContainer.getChildren().add(bookItem);
        }

        Scene detailScene = new Scene(root, 1021, 644);
        detailStage.setScene(detailScene);
        detailStage.setTitle("Book Details");

        BookController bookController = loader1.getController();
        bookController.setParentController(this);
        detailStage.show();
    }


    public void showAvailableBooks() throws IOException {
        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        String bookViewQuery = "SELECT * FROM library_books";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookItem.fxml"));
                VBox bookItem = loader.load();

                ImageView bookImage = (ImageView) bookItem.lookup("#bookImage");
                Label titleLabel = (Label) bookItem.lookup("#titleLabel");
                Label authorLabel = (Label) bookItem.lookup("#authorLabel");

                String title = queryOutput.getString("title");
                String genre = queryOutput.getString("category");
                String author = queryOutput.getString("author");
                int quantity = queryOutput.getInt("quantity");
                String image = queryOutput.getString("image");
                Date date = queryOutput.getDate("publishedDate");
                String description = queryOutput.getString("description");

                Book b1 = new Book(title, author, image, description);

                bookItem.setOnMouseClicked(event -> {
                    // Handle click event
                    try {
                        GetData.title = b1.getTitle();
                        showBookDetails(b1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Set book details
                bookImage.setImage(new Image(image, true));
                titleLabel.setText(title);
                authorLabel.setText(author);

                bookContainer1.getChildren().add(bookItem);
            }
        } catch (SQLException e) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }


    }

    /*public void selectAvailableBooks() {

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

        String uri = "file:" + bookData.getImage();

        image = new Image(uri, 134, 170, false, true);

        availableBooks_imageView.setImage(image);

        GetData.bookId = bookData.getBookId();
    }*/



    public void sideNavButtonDesign(ActionEvent event) {

        if (event.getSource() == halfNav_availableBtn) {

            availableBooks_form.setVisible(true);
            borrowBook_form.setVisible(false);
            availableBooks1_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            borrowBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks1_form.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn1.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Books API");

            GetData.borrowId = 0;

        } else if (event.getSource() == halfNav_availableBtn1) {

            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(false);
            availableBooks1_form.setVisible(true);

            borrowBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn1.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn1.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");

            currentForm_label.setText("Available Books");
        }
        else if (event.getSource() == halfNav_borrowBtn) {
            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(true);
            availableBooks1_form.setVisible(false);

            borrowBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            availableBooks_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn1.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_borrowBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_availableBtn.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn1.setStyle("-fx-background-color:linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Borrowed Books");

            showBorrowBooks();
        }
    }

    public void navButtonDesign(ActionEvent event) {

        if (event.getSource() == availableBooks_btn) {
            availableBooks_form.setVisible(true);
            borrowBook_form.setVisible(false);
            availableBooks1_form.setVisible(false);

            availableBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            borrowBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks1_form.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_availableBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_borrowBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn1.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Books API");

            GetData.borrowId = 0;

        } else if (event.getSource() == borrowBooks_btn) {

            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(true);
            availableBooks1_form.setVisible(false);

            borrowBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            availableBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn1.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            halfNav_borrowBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_availableBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_availableBtn1.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Borrowed Books");

            showBorrowBooks();

        }
        else if (event.getSource() == availableBooks_btn1) {
            availableBooks_form.setVisible(false);
            borrowBook_form.setVisible(false);
            availableBooks1_form.setVisible(true);

            borrowBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            availableBooks_btn1.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");

            halfNav_availableBtn1.setStyle("-fx-background-color: linear-gradient(to bottom right, #F0B000, #F0B000);");
            halfNav_availableBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");
            halfNav_borrowBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #CC9600, #CC9600);");

            currentForm_label.setText("Available Books");
        }
    }

    public void userName() {
        userName_label.setText(GetData.username);
    }

    ObservableList<BorrowBooks> borrowBookObservableList = FXCollections.observableArrayList();

    public void showBorrowBooks()  {
        borrowBookObservableList.clear();

        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        LocalDate localDate = LocalDate.now();  // Lấy ngày hiện tại
        java.sql.Date sqlDate = Date.valueOf(localDate); // Chuyển thành java.sql.Date

        String bookViewQuery = "SELECT b.borrow_id, lb.book_id, b.title, b.borrow_date, b.return_date, lb.image, " +
                "IF(b.return_date >= '" + sqlDate + "', 'pending', 'overdue') AS status " +
                "FROM borrow_books b " +
                "JOIN library_books lb ON b.title = lb.title " +
                "WHERE b.username = '" + GetData.username + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while (queryOutput.next()) {
                int borrowId = queryOutput.getInt("borrow_id");
                int bookId = queryOutput.getInt("book_id");
                String title = queryOutput.getString("title");
                Date returnDate = queryOutput.getDate("return_date");
                Date borrowDate = queryOutput.getDate("borrow_date");
                String image = queryOutput.getString("image");
                String status = queryOutput.getString("status");


                borrowBookObservableList.add(new BorrowBooks(borrowId, bookId, title, borrowDate, returnDate, image, status));
            }

            col_borrowId.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
            col_borrowBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
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

    // chua sua
    public void selectBorrowBooks() {

        BorrowBooks sBook = borrow_tableView.getSelectionModel().getSelectedItem();
        int num = borrow_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        //String uri = "file:" + sBook.getImage();

        borrow_imageView.setImage(new Image(sBook.getImage(), true));

        //GetData.bookId = sBook.getBookId();
        //GetData.borrowDate = sBook.getBorrowDate();
        //GetData.returnDate = sBook.getReturnDate();
        GetData.borrowId = sBook.getBorrowId();
    }

    /*public void returnBooks() {

        String sql = "DELETE FROM borrow_books WHERE borrow_id = '" + GetData.borrowId + "'";

        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        try {

            Alert alert;

            if (borrow_tableView.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Select the book you want to Return");
                alert.showAndWait();

            } else {

                Statement statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Return.");
                alert.showAndWait();

                //showBorrowBooks();

                borrow_imageView.setImage(null);
                showBorrowBooks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public void returnBooks() {
        if (borrow_tableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the book you want to return.");
            alert.showAndWait();
            return;
        }

        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        try {
            Alert alert;

            // Lấy thông tin sách từ bảng borrow_tableView
            BorrowBooks selectedBook = borrow_tableView.getSelectionModel().getSelectedItem();
            String selectedTitle = selectedBook.getTitle();

            // Xóa sách khỏi bảng borrow_books
            String deleteSql = "DELETE FROM borrow_books WHERE borrow_id = ?";
            PreparedStatement deleteStatement = connectDB.prepareStatement(deleteSql);
            deleteStatement.setInt(1, selectedBook.getBorrowId());
            deleteStatement.executeUpdate();

            // Tăng số lượng sách trong bảng library_books
            String updateQuantitySql = "UPDATE library_books SET quantity = quantity + 1 WHERE title = ?";
            PreparedStatement updateQuantityStatement = connectDB.prepareStatement(updateQuantitySql);
            updateQuantityStatement.setString(1, selectedTitle);
            updateQuantityStatement.executeUpdate();

            // Thông báo thành công
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully returned the book.");
            alert.showAndWait();

            // Cập nhật giao diện
            borrow_imageView.setImage(null);
            showBorrowBooks();

        } catch (Exception e) {
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
        try {
            showAvailableBooks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userName();
        //showBorrowBooks();
    }

    @FXML
    private ImageView avatarView;

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

    @FXML
    private TextField searchTextField;

    @FXML
    private TilePane bookContainer;

    private List<Book> bookList = new ArrayList<>();

    // A map to store book titles and their descriptions
    private final Map<String, Book> books = new HashMap<>();
    private static final String API_KEY = "AIzaSyDNTqgG7GX4_NpaUbN03qLA4Z-3jrMmyL0";



    @FXML
    private Button generateQRButton;

    @FXML
    private void generateQRButtonOnAction(ActionEvent event) {
        // Tạo một luồng mới để xử lý việc tạo QR và file HTML
        new Thread(() -> {
            try {
                // Lấy thông tin sách được chọn từ bảng
                BorrowBooks selectedBook = borrow_tableView.getSelectionModel().getSelectedItem();
                if (selectedBook == null) {
                    showError("No book selected", "Please select a book from the table.");
                    return;
                }

                // Lấy thông tin sách
                String bookId = String.valueOf(selectedBook.getBookId());
                String title = selectedBook.getTitle();
                String borrowDate = selectedBook.getBorrowDate().toString();
                String returnDate = selectedBook.getReturnDate().toString();
                String user = selectedBook.getUserName();

                // Tạo file HTML
                generateHtmlFile(bookId, title, borrowDate, returnDate, user);

                // Tạo QR Code
                BufferedImage qrImage = generateQRCode(bookId, 300, 300);

                // Hiển thị mã QR trong pop-up
                WritableImage qrFXImage = convertToWritableImage(qrImage);

                // Cập nhật giao diện người dùng từ luồng chính
                javafx.application.Platform.runLater(() -> showQRCodePopup(qrFXImage));
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> showError("Error", "QR GENERATE ERROR."));
            }
        }).start();
    }

    private void generateHtmlFile(String id, String title, String borrowDate, String returnDate, String user) throws IOException {
        String htmlContent = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>%s</title>
            <link rel="stylesheet" href="styles.css"> <!-- Kết nối file CSS -->
        </head>
        <body>
            <div class="container">
                <h1>%s</h1>
                <p><strong>Book ID:</strong> %s</p>
                <p><strong>User:</strong> %s</p>
                <p><strong>Borrow Date:</strong> %s</p>
                <p><strong>Return Date:</strong> %s</p>
            </div>
            <footer>
                <p>@LibraryManagementSystem2024</p>
            </footer>
        </body>
        </html>
        """.formatted(title, title, id, user, borrowDate, returnDate);

        Path filePath = Paths.get("local_html/books/" + id + ".html");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, htmlContent);
    }


    private BufferedImage generateQRCode(String id, int width, int height) throws WriterException, IOException {
        String ip = "192.168.241.186"; // Thay bằng IP máy tính của bạn
        String qrText = "http://" + ip + ":8000/books/" + id + ".html";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

        Path filePath = Paths.get("local_html/qrcodes/" + id + ".png");
        Files.createDirectories(filePath.getParent());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private WritableImage convertToWritableImage(BufferedImage qrImage) {
        WritableImage writableImage = new WritableImage(qrImage.getWidth(), qrImage.getHeight());
        javafx.scene.image.PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < qrImage.getWidth(); x++) {
            for (int y = 0; y < qrImage.getHeight(); y++) {
                pixelWriter.setArgb(x, y, qrImage.getRGB(x, y));
            }
        }

        return writableImage;
    }

    private void showQRCodePopup(WritableImage qrImage) {
        Stage popupStage = new Stage();
        popupStage.setTitle("QR Code");

        ImageView imageView = new ImageView(qrImage);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        VBox root = new VBox(imageView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 350, 350);
        popupStage.setScene(scene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    @FXML
    public void initialize() {
        // Set up event listener for book selection
//        bookListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
//            if (newValue != null) {
//                descriptionTextArea.setText(books.get(newValue).getDescription());
//                bookImage.setImage(new Image(books.get(newValue).getImageUrl()));
//            }
//        });
        showBorrowBooks();
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText().trim();
        //String query = "fiction";
        if (query.isEmpty()) {
            //descriptionTextArea.setText("Please enter a search query.");
            return;
        }

        // Clear previous results
        books.clear();
        bookList.clear();
        bookContainer.getChildren().clear();
        //descriptionTextArea.clear();

        try {
            // Perform HTTP request
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray itemsArray = jsonResponse.optJSONArray("items");

            if (itemsArray != null) {
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject item = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                    if (volumeInfo != null) {
                        String title = volumeInfo.optString("title", "Unknown Title");
                        String description = volumeInfo.optString("description", "No description available.");
                        String author = volumeInfo.optString("authors", "Unkown Author");
                        String publisher = volumeInfo.optString("publisher", "Unkown Publisher");
                        String category = volumeInfo.optString("categories", "No category");
                        int publishedDate = volumeInfo.optInt("publishedDate", 0);
                        int pageCount = volumeInfo.optInt("pageCount", 999);

                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                        String imageUrl = imageLinks.getString("thumbnail");

                        author = author.substring(2, author.length() - 2);
                        // Store in map

                        Book book = new Book(title, author, imageUrl, description, publishedDate, pageCount, publisher, category);
                        books.put(title, book);

                        // Update the UI on the JavaFX Application Thread
                        bookList.add(book);

                        //Platform.runLater(() -> bookList.add(Book);
//                            Platform.runLater(() -> {
//                                bookImage.setImage(new Image(imageUrl));
//                            });
                    }
                }
            } else {
                //Platform.runLater(() -> bookListView.getItems().add("No books found."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Platform.runLater(() -> bookListView.getItems().add("Failed to load books."));
        }

        for (Book book : bookList) {
            try {
                addBookToUI(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addBookToUI(Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookItem.fxml"));
        VBox bookItem = loader.load();

        ImageView bookImage = (ImageView) bookItem.lookup("#bookImage");
        Label titleLabel = (Label) bookItem.lookup("#titleLabel");
        Label authorLabel = (Label) bookItem.lookup("#authorLabel");

        // Set book details
        bookImage.setImage(new Image(book.getImageUrl(), true));
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());

        bookItem.setOnMouseClicked(event -> {
            // Handle click event
            try {
                showBookDetails(book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add to container
        bookContainer.getChildren().add(bookItem);
    }

    public List<Book> relatedBookList(Book book) {

        List<Book> bookList = new ArrayList<>();

        try {

            // Encode the query for safe use in URLs
            String encodedQuery = URLEncoder.encode(book.getTitle(), StandardCharsets.UTF_8);
            //System.out.println(encodedQuery);
            String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + encodedQuery + "&key=" + API_KEY;

            // Create HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            // Send the HTTP request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response is successful
            if (response.statusCode() == 200) {
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONArray itemsArray = jsonResponse.optJSONArray("items");

                if (itemsArray != null) {
                    // Extract related book details
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject item = itemsArray.getJSONObject(i);
                        JSONObject volumeInfo = item.optJSONObject("volumeInfo");

                        if (volumeInfo != null) {
                            String title = volumeInfo.optString("title", "Unknown Title");

                            if (title.equals(book.getTitle())) continue;

                            String description = volumeInfo.optString("description", "No description available.");
                            String author = volumeInfo.optString("authors", "Unkown Author");
                            String publisher = volumeInfo.optString("publisher", "Unkown Publisher");
                            String category = volumeInfo.optString("Fiction", "No category");
                            int publishedDate = volumeInfo.optInt("publishedDate", 0);
                            int pageCount = volumeInfo.optInt("pageCount", 999);

                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            String imageUrl = imageLinks.getString("thumbnail");

                            author = author.substring(2, author.length() - 2);
                            // Store in map

                            Book book1 = new Book(title, author, imageUrl, description, publishedDate, pageCount, publisher, category);
                            //books.put(title, book);

                            // Update the UI on the JavaFX Application Thread
                            bookList.add(book);

                            //Platform.runLater(() -> bookList.add(Book);
//                            Platform.runLater(() -> {
//                                bookImage.setImage(new Image(imageUrl));
//                            });
                        }
                    }
                }
            } else {
                System.err.println("Failed to fetch related books. HTTP Status: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }


}
