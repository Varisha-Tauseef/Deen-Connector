package com.example.summer_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {

    public static User user;

    public static SessionManager session;

    public static Settings setting;

    public static String email;

    public static String password;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load your FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/summer_project/step1.fxml"));
        Scene scene = new Scene(loader.load());

        // Set scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("Deen Connector");

        // Lock the window size to 1280x720
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.setMaxWidth(1280);
        primaryStage.setMaxHeight(720);
        primaryStage.setResizable(false); // Optional: disables manual resizing

        // Reset size when exiting fullscreen
        primaryStage.fullScreenProperty().addListener((obs, wasFull, isNowFull) -> {
            if (!isNowFull) {
                primaryStage.setWidth(1280);
                primaryStage.setHeight(720);
                primaryStage.centerOnScreen();
            }
        });

        // Show the window
        primaryStage.show();
    }
}

