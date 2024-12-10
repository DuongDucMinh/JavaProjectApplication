package com.example.javafxapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;

public class BookCategoryController {

    @FXML
    private TabPane tabPane;

    @FXML
    private ListView<String> popularListView;

    @FXML
    private ListView<String> underFiveListView;

    @FXML
    private ListView<String> agesSixToEightListView;

    @FXML
    private ListView<String> agesNineToTwelveListView;

    @FXML
    private ListView<String> audioListView;

    @FXML
    public void initialize() {
        // Add data to the "Popular" category
        popularListView.getItems().addAll(
                "Diary of a Wimpy Kid",
                "Clifford the Big Red Dog",
                "National Geographic Kids"
        );

        // Add data to the "5 & Under" category
        underFiveListView.getItems().addAll(
                "Elmo: Monsters in the Bathroom",
                "Cars: Audiobook"
        );

        // Add data to the "Ages 6-8" category
        agesSixToEightListView.getItems().addAll(
                "Big Nate: I Can't Take It!",
                "Guinea Dog"
        );

        // Add data to the "Ages 9-12" category
        agesNineToTwelveListView.getItems().addAll(
                "I Survived: Audiobook",
                "Rosie Revere, Engineer"
        );

        // Add data to the "Audio" category
        audioListView.getItems().addAll(
                "I Want My Hat Back",
                "Wild Animal Atlas"
        );
    }

    @FXML
    private void onPreviousClicked() {
        int currentIndex = tabPane.getSelectionModel().getSelectedIndex();
        int previousIndex = (currentIndex - 1 + tabPane.getTabs().size()) % tabPane.getTabs().size();
        tabPane.getSelectionModel().select(previousIndex);
    }

    @FXML
    private void onNextClicked() {
        int currentIndex = tabPane.getSelectionModel().getSelectedIndex();
        int nextIndex = (currentIndex + 1) % tabPane.getTabs().size();
        tabPane.getSelectionModel().select(nextIndex);
    }
}
