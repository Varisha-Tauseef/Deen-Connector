package com.example.summer_project.gui;

import com.example.summer_project.ManageScene;
import com.example.summer_project.main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class Step6Controller {

    @FXML
    void goToCalendar(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("calendar.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToEmotionDetector(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("emotion_classifier.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToSalahSteps(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("step1.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToHome(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("dashboard.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToPage5(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("step5.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToPage7(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("step7.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToSetting(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("setting.fxml")
            ));

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

