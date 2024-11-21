module com.example.javafxapp {
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires javafx.controls;

    requires transitive de.jensd.fx.glyphs.fontawesome;
    opens com.example.javafxapp to javafx.fxml;
    exports com.example.javafxapp;
}