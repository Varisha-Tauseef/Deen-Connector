package com.example.summer_project.gui;

import com.example.summer_project.SalahSteps;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

public class WebcamQiyamController {

    @FXML private ImageView imageView;

    @FXML private Label timerLabel;

    private ScheduledExecutorService executor;

    private Webcam webcam;

    @FXML
    public void initialize() {
        startCamera();
    }

    private void startCamera() {
        if (webcam == null) {
            webcam = Webcam.getDefault();
            if (webcam != null) {
                webcam.open();
            }
        }

        executor = Executors.newScheduledThreadPool(2);

        // Countdown for 20 seconds
        final int[] countdown = {20};
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> timerLabel.setText("Get ready: " + countdown[0] + "s left"));
            countdown[0]--;

            if (countdown[0] < 0) {
                captureAndSend();
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Stream webcam continuously
        executor.scheduleAtFixedRate(() -> {
            if (webcam != null && webcam.isOpen()) {
                BufferedImage img = webcam.getImage();
                if (img != null) {
                    WritableImage fxImage = SwingFXUtils.toFXImage(img, null);
                    Platform.runLater(() -> imageView.setImage(fxImage));
                }
            }
        }, 0, 33, TimeUnit.MILLISECONDS); // ~30fps
    }

    private void captureAndSend() {
        if (webcam != null && webcam.isOpen()) {
            BufferedImage img = webcam.getImage();

            try {
                File file = new File("capture.jpg");
                ImageIO.write(img, "JPG", file);

                Platform.runLater(() -> timerLabel.setText("Captured! Sending to API..."));

                JSONObject response = SalahSteps.sendToAPI(file.getAbsolutePath(), "qiyam_hands");

                if (response != null) {
                    String message = response.optString("message", "No message");
                    Platform.runLater(() -> {
                        timerLabel.setText("Response: " + message);

                        // Auto-close only this window after 10 seconds
                        PauseTransition delay = new PauseTransition(Duration.seconds(10));
                        delay.setOnFinished(event -> {
                            Stage stage = (Stage) timerLabel.getScene().getWindow();
                            stage.close();
                        });
                        delay.play();
                    });
                } else {
                    Platform.runLater(() -> timerLabel.setText("Error: No response from API"));

                    // Auto-close only this window after 10 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(10));
                    delay.setOnFinished(event -> {
                        Stage stage = (Stage) timerLabel.getScene().getWindow();
                        stage.close();
                    });
                    delay.play();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                stopCamera();
            }
        }
    }

    private void stopCamera() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }
}
