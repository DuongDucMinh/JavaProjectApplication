module sample.logingui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens sample.logingui to javafx.fxml;
    exports sample.logingui;
}