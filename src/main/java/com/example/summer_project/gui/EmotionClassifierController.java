package com.example.summer_project.gui;

import com.example.summer_project.EmotionAnalyzer;
import com.example.summer_project.ManageScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.Objects;

public class EmotionClassifierController {

    @FXML
    private TextArea output;

    @FXML
    private TextField userFeeling;

    @FXML
    void handleUserInput(ActionEvent event) {
        String input = userFeeling.getText();

        if (input == null || input.isBlank()) {
            output.setText("Please enter how you are feeling.");
            return;
        }

        // Detect emotion subcategory
        String subcategory = EmotionAnalyzer.detectEmotion(input);

        if (subcategory == null) {
            output.setText("Could not detect emotion. Backend may not be running.");
            return;
        }

        // Fetch a random dua or verse for the detected subcategory
        JSONObject duaOrVerse = EmotionAnalyzer.getRandomDuaOrVerse(subcategory);

        if (duaOrVerse == null) {
            output.setText("No dua or verse found for subcategory: " + subcategory);
            return;
        }

        // Build a nicely formatted output
        StringBuilder formattedOutput = new StringBuilder();
        formattedOutput.append("Detected Emotion: ").append(subcategory.toUpperCase()).append("\n\n");

        if (duaOrVerse.has("arabic")) {
            formattedOutput.append(duaOrVerse.optString("arabic")).append("\n\n");
            formattedOutput.append(duaOrVerse.optString("transliteration")).append("\n\n");
            formattedOutput.append(duaOrVerse.optString("translation"));
        } else {
            formattedOutput.append("Reference:\n").append(duaOrVerse.optString("reference", "N/A")).append("\n\n");
            formattedOutput.append("Text:\n").append(duaOrVerse.optString("text", "N/A"));
        }
        output.setText(formattedOutput.toString());
    }

    @FXML
    void goToCalendar(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("calendar.fxml")
            ));

            Scene scene = new Scene(root);

            // Get the current stage from the event source
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

            // Get the current stage from the event source
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

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleHome(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("dashboard.fxml")
            ));

            Scene scene = new Scene(root);

            // Get the current stage from the event source
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
