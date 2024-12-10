package com.example.javafxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;

public class BookListController {

    @FXML
    private TilePane bookContainer;

    public void initialize() {
        // Example book list
        List<Book> books = List.of(
                new Book("Moby Dick", "Herman Melville", "https://example.com/mobydick.jpg"),
                new Book("1984", "George Orwell", "https://example.com/1984.jpg"),
                new Book("Brave New World", "Aldous Huxley", "https://example.com/bravenewworld.jpg"),
                new Book("Brave New World", "Aldous Huxley", "https://example.com/bravenewworld.jpg"),
                new Book("Brave New World", "Aldous Huxley", "https://example.com/bravenewworld.jpg"),
                new Book("Brave New World", "Aldous Huxley", "https://example.com/bravenewworld.jpg"),
                new Book("Brave New World", "Aldous Huxley", "https://example.com/bravenewworld.jpg")
                // Add more books here...
        );

        for (Book book : books) {
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
        //bookImage.setImage(new Image(book.getImageUrl(), true));
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());

        // Add to container
        bookContainer.getChildren().add(bookItem);
    }
}
