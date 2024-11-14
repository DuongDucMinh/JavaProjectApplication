package sample.logingui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSearchController implements Initializable {
    @FXML
    private TextField keywordTextField;

    @FXML
    private TableView<UserSearchModel> userTableView;

    @FXML
    private TableColumn<UserSearchModel, String> userFirstNameColumn;

    @FXML
    private TableColumn<UserSearchModel, String> userLastNameColumn;

    @FXML
    private TableColumn<UserSearchModel, Integer> userIDTableColumn;

    ObservableList<UserSearchModel> userSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String userViewQuery = "SELECT customerNumber,contactLastName, contactFirstName FROM customers";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userViewQuery);

            while (queryOutput.next()) {
                int userID = queryOutput.getInt("customerNumber");
                String firstName = queryOutput.getString("contactFirstName");
                String lastName = queryOutput.getString("contactLastName");

                userSearchModelObservableList.add(new UserSearchModel(userID, firstName, lastName));
            }

            userIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
            userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            userLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

            userTableView.setItems(userSearchModelObservableList);

            // Danh sach loc ban dau
            FilteredList<UserSearchModel> filteredData = new FilteredList<>(userSearchModelObservableList, b -> true);

            // Thêm một ChangeListener vào thuộc tính textProperty() của keywordTextField => lắng nghe sự thay đổi của văn bản trong TextField.
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Cập nhật điều kiện lọc (Predicate) của filteredData mỗi khi văn bản trong TextField thay đổi.
                filteredData.setPredicate(userSearchModel -> {

                    if (newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String searchKeywords = newValue.toLowerCase();
                    // no match found
                    return userSearchModel.getFirstName().toLowerCase().contains(searchKeywords)
                            || userSearchModel.getLastName().toLowerCase().contains(searchKeywords)
                            || (userSearchModel.getUserID() + "").contains(searchKeywords);
                });
            });

            SortedList<UserSearchModel> sortedData = new SortedList<>(filteredData);

            // Liên kết kết quả được sắp xếp với Chế độ xem bảng
            sortedData.comparatorProperty().bind(userTableView.comparatorProperty());

            // Apply filtered and sorted data to the Table View
            userTableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(UserSearchController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
}