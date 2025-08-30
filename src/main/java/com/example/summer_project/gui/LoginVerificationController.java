package com.example.summer_project.gui;

import com.example.summer_project.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Objects;

import static com.example.summer_project.main.session;
import static com.example.summer_project.main.setting;
import static com.example.summer_project.main.user;


public class LoginVerificationController {

    @FXML
    private TextField userCode;

    @FXML
    void backToLogin(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("login.fxml")
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
    void handleVerify(ActionEvent event) throws IOException {
        // check if the code entered by the user matches the one program generated
        if (LoginController.loginCode.equals(userCode.getText())) {

            // Create User object
            String localId = LoginController.loginResponse.getString("localId");
            main.user = new User(main.email, localId);

            // Create SessionManager object
            String idToken = LoginController.loginResponse.getString("idToken");
            String refreshToken = LoginController.loginResponse.getString("refreshToken");
            session = new SessionManager(idToken, refreshToken);

            // Create settings object
            setting = new Settings(user, session);

            // Load username
            JSONObject response = FirebaseDatabaseService.loadUserData(session.getIdToken(), user.getLocalId());

            if (response.has("username")) {
                user.setUsername(response.getString("username"));
            }

            // Open dashboard.fxml
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
            // Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Verification Code Error");
            alert.setHeaderText("The verification code is incorrect. Please enter the code that was sent to your email.");
            alert.showAndWait();
        }
    }
}


