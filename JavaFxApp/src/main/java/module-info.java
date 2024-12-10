module com.example.javafxapp {
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires javafx.controls;
    requires com.google.gson;


    requires transitive de.jensd.fx.glyphs.fontawesome;
    requires java.net.http;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;
    requires jdk.httpserver;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    opens com.example.javafxapp to javafx.fxml, com.google.gson;

    exports com.example.javafxapp;
}