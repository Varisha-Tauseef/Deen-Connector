module com.example.summer_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires okhttp3;
    requires jakarta.mail;
    requires org.json;
    requires json.simple;
    requires java.desktop;
    requires webcam.capture;
    requires javafx.swing;


    opens com.example.summer_project to javafx.fxml;
    exports com.example.summer_project;
    exports com.example.summer_project.gui;
    opens com.example.summer_project.gui to javafx.fxml;
}