package com.example.javafxapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class ManagerBookController {

    @FXML
    private TextField searchTextField;

    @FXML
    private TilePane bookContainer;

    @FXML
    private Button addBookButton;

    private List<Book> bookList = new ArrayList<>();
    //private Book curBook;

    //private DashboardController parentController;

    // A map to store book titles and their descriptions
    private final Map<String, Book> books = new HashMap<>();
    private static final String API_KEY = "AIzaSyDNTqgG7GX4_NpaUbN03qLA4Z-3jrMmyL0";

//    public void setParentController(DashboardController parentController) {
//        this.parentController = parentController;
//    }

    @FXML
    public void initialize() {
        // Set up event listener for book selection
//        bookListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
//            if (newValue != null) {
//                descriptionTextArea.setText(books.get(newValue).getDescription());
//                bookImage.setImage(new Image(books.get(newValue).getImageUrl()));
//            }
//        });
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

                        if (!category.equals("No category")) {
                            category = category.substring(2, category.length() - 2);
                        }

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
                GetData.title = book.getTitle();
                GetData.author = book.getAuthor();
                GetData.category = book.getCategory();
                GetData.description = book.getDescription();
                GetData.pathImage = book.getImageUrl();
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
            String encodedQuery = URLEncoder.encode(book.getCategory(), StandardCharsets.UTF_8);
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
                            String category = volumeInfo.optString("categories", "No category");

                            if (!category.equals("No category")) {
                                category = category.substring(2, category.length() - 2);
                            }

                            int publishedDate = volumeInfo.optInt("publishedDate", 0);
                            int pageCount = volumeInfo.optInt("pageCount", 999);

                            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                            String imageUrl = imageLinks.getString("thumbnail");

                            author = author.substring(2, author.length() - 2);
                            // Store in map

                            Book book1 = new Book(title, author, imageUrl, description, publishedDate, pageCount, publisher, category);
                            //books.put(title, book);

                            // Update the UI on the JavaFX Application Thread
                            bookList.add(book1);

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


    private void showBookDetails(Book book) throws IOException {

        Stage detailStage = new Stage();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("managerbookDetails.fxml"));
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

        Label categories = (Label) namespace.get("categories");
        categories.setText(book.getCategory());

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
        detailStage.show();
    }

    @FXML
    private TextField quantityField;

    @FXML
    private void insertToAvailableBooks() throws Exception {
        String bookTitle = GetData.title;
        if (GetData.title.length() >= 45) bookTitle = bookTitle.substring(0, 45);
        String checkSql = "SELECT * FROM library_books WHERE title = '" + bookTitle + "'";

        DatabaseConnection connectNow = DatabaseConnection.getInstance();
        Connection connectDB = connectNow.getDBConnection();

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(checkSql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Alert alert;
            if (resultSet.next()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("You added this book to the library!");
                alert.showAndWait();

            } else {
                String category = GetData.category;
                if (GetData.category.length() >= 45) category = category.substring(0, 45);
                String author = GetData.author;
                if (GetData.author.length() >= 45) author = author.substring(0, 45);
                String description = GetData.description;
                if (description.length() > 500) description = description.substring(0, 500);
                String image = GetData.pathImage;
                String publishedDate = GetData.getRandomPublishedDate(1990, 2004);
                String quantity = quantityField.getText();

                String insertFields = "INSERT INTO library_books(title, category, author, publishedDate, quantity, image, description) " +
                        "VALUES('";
                String insertValues = bookTitle + "','" + category + "','" + author + "','" + publishedDate + "','" + quantity + "','" + image + "','"
                        + description + "')";
                String insertToBooks = insertFields + insertValues;

                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertToBooks);

                addBookButton.setVisible(false);


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added.");
                alert.showAndWait();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


        if (parentController != null) {
            parentController.showAvailableBooks();
        } else {
            System.err.println("ParentController is not set!");
        }

    }

    private static DashboardController parentController;

    public static void setParentController(DashboardController controller) {
        parentController = controller;
    }

    public static DashboardController getParentController() {
        return parentController;
    }
}
