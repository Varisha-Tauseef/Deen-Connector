package com.example.summer_project.gui;

import com.example.summer_project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;

import static com.example.summer_project.main.email;
import static com.example.summer_project.main.password;


public class RegisterController {

    public static String registerCode;

    public static JSONObject registrationResponse;

    public static String username;

    @FXML
    private TextField emailField;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameField;

    @FXML
    void handleLoginLink(ActionEvent event) {
        // Open the login.fxml
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("login.fxml")
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
    void handleSignup(ActionEvent event) throws Exception {
        email = emailField.getText();
        password = passwordField.getText();
        username = usernameField.getText();

        // Check if username field is empty
        if (username == null || username.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input Required");
            alert.setHeaderText(null);
            alert.setContentText("Please enter username.");
            alert.showAndWait();
            return;
        }

        // Check if password meets requirements
        if (!Verifier.verifyPassword(password)) {
            return;
        }

        // Check if email is a valid Gmail
        if (!Verifier.verifyGmail(email)) {
            return;
        }

        // Send registration request to firebase
        registrationResponse = FirebaseAuthService.registerUser(email, password);

        // Check what the response is
        if (registrationResponse.has("error")) {
            JSONObject errorObj = registrationResponse.getJSONObject("error");

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
            alert.setTitle("Register Error");
            alert.setHeaderText(message);
            alert.setContentText(reason.isEmpty() ? "Please try again." : reason);
            alert.showAndWait();
        } else {
            // Create User object
            String localId = registrationResponse.getString("localId");
            main.user = new User(main.email, localId);
            main.user.setUsername(username);

            // Create SessionManager object
            String idToken = registrationResponse.getString("idToken");
            String refreshToken = registrationResponse.getString("refreshToken");
            main.session = new SessionManager(idToken, refreshToken);

            // Store username in the database
            JSONObject username = FirebaseDatabaseService.createUserDataJson(main.user.getUsername());
            JSONObject response = FirebaseDatabaseService.storeUserData(main.session.getIdToken(), main.user.getLocalId(), username);

            // Generate and send 2FA code
            registerCode = String.valueOf(EmailService.codeGenerator());
            EmailService.send2FACode(email, registerCode);

            // Switch to register_verification scene
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(
                        SessionManager.class.getResource("register_verification.fxml")
                ));

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.sizeToScene();
                stage.show();

            } catch (Exception e) {
                // Delete the user
                FirebaseAuthService.deleteUser(main.session.getIdToken());
                e.printStackTrace();
            }
        }
    }
}

