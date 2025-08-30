package com.example.summer_project;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManageScene {
    public static void switchScene(String fxmlFile, Stage stage) throws Exception {
        Parent root = FXMLLoader.load(ManageScene.class.getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
