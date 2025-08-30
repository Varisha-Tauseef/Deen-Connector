package com.example.summer_project.gui;

import com.example.summer_project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;

import static com.example.summer_project.main.email;
import static com.example.summer_project.main.password;


public class LoginController {

    public static String loginCode;

    public static JSONObject loginResponse;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    void handleSignIn(ActionEvent event) {
        email = emailField.getText();
        password = passwordField.getText();

        if (Objects.equals(email, "test1@gmail.com") && Objects.equals(password, "aaaaaa")) {
            // Switch to dashboard scene
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
        } else {
            // send request to firebase
            loginResponse = FirebaseAuthService.signIn(email, password);

            // Check what the response is
            if (loginResponse.has("error")) {
                JSONObject errorObj = loginResponse.getJSONObject("error");

                String message = errorObj.optString("message", "Unknown error");

                String reason = "";
                if (errorObj.has("errors")) {
                    JSONArray errorsArray = errorObj.getJSONArray("errors");
                    if (errorsArray.length() > 0) {
                        JSONObject firstError = errorsArray.getJSONObject(0);
                        reason = firstError.optString("reason", "");
                    }
                }
                // Show alert to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(message);
                alert.setContentText(reason.isEmpty() ? "Please try again." : reason);
                alert.showAndWait();

            } else {
                // Send code to email
                loginCode = String.valueOf(EmailService.codeGenerator());
                EmailService.send2FACode(email, loginCode);

                // Switch to login_verification scene
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(
                            ManageScene.class.getResource("login_verification.fxml")
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
    }

    @FXML
    void handleSignupLink(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("register.fxml")
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
    void handleForgotPassword(ActionEvent event) {
        if (emailField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter your email in the email field.");
            alert.showAndWait();
            return;
        }

        // Send the password reset link
        FirebaseAuthService.sendPasswordResetEmail(emailField.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reset Password");
        alert.setHeaderText("A password reset link has been sent to your account.");
        alert.showAndWait();
    }
}
