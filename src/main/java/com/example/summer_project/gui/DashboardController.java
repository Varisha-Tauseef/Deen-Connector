package com.example.summer_project.gui;

import com.example.summer_project.ManageScene;
import com.example.summer_project.PrayerTimings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.Objects;

import static com.example.summer_project.main.user;

public class DashboardController {

    @FXML
    private Label asrTime;

    @FXML
    private Label dhuhrTime;

    @FXML
    private Label fajrTime;

    @FXML
    private Label ishaTime;

    @FXML
    private Label maghribTime;

    @FXML
    private Label qiyamTime;

    @FXML
    private Label sunriseTime;

    @FXML
    private Label usersUsername;

    @FXML
    public void initialize() {
       PrayerTimings timings = new PrayerTimings();

       // Load prayer timings
       asrTime.setText(timings.getAsrTiming());
       dhuhrTime.setText(timings.getDhuhrTiming());
       fajrTime.setText(timings.getFajrTiming());
       ishaTime.setText(timings.getIshaTiming());
       maghribTime.setText(timings.getMaghribTiming());
       qiyamTime.setText(timings.getQiyamTiming());
       sunriseTime.setText(timings.getSunriseTiming());

       // Load username
       String username = user.getUsername();
       usersUsername.setText(username);
    }

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
    void goToSettings(MouseEvent event) {
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
