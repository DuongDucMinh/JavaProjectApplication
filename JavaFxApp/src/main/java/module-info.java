module com.example.javafxapp {
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires transitive de.jensd.fx.glyphs.fontawesome;
    requires javafx.controls;

    opens com.example.javafxapp to javafx.fxml;
    exports com.example.javafxapp;
}