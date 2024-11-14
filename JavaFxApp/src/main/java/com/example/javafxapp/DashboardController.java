package com.example.javafxapp;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class DashboardController implements Initializable {

    @FXML
    private Button availableBooks_btn;

    @FXML
    private AnchorPane availableBooks_form;

    @FXML
    private ImageView availableBooks_imageView;

    @FXML
    private Label availableBooks_title;

    @FXML
    private Circle circle_image;

    @FXML
    private TableColumn<?, ?> col_ab_Author;

    @FXML
    private TableView<?> col_ab_bookTitle;

    @FXML
    private TableColumn<?, ?> col_ab_category;

    @FXML
    private TableColumn<?, ?> col_ab_publishedDate;

    /*@FXML
    private FontAwesomeIconView edit_btn;*/

    @FXML
    private Button issueBooks_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button returnBooks_btn;

    @FXML
    private Button save_btn;

    @FXML
    private Button savedBooks_btn;

    @FXML
    private Button take_btn;

    @FXML
    private Label userName_label;

    @FXML
    private TableView<?> availableBooks_tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
