package com.example.javafxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       // FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("menu-manager-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setTitle("User - Search");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}