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

import java.util.Objects;

public class RegisterVerificationController {

    @FXML
    private TextField userCode;

    @FXML
    void handleVerify(ActionEvent event) {

        // check if the code entered by the user matches the one program generated
        if (RegisterController.registerCode.equals(userCode.getText())) {

            // Open login.fxml
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
                // Delete the user
                FirebaseAuthService.deleteUser(main.session.getIdToken());
                main.user = null;
                main.session = null;
                e.printStackTrace();
            }
        } else {
            // Delete the user
            FirebaseAuthService.deleteUser(main.session.getIdToken());
            // Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Verification Code Error");
            alert.setHeaderText("The verification code is incorrect. Please enter the code that was sent to your email.");
            alert.showAndWait();
            }
        }

    @FXML
    void backToRegister(MouseEvent event) {
        try {
            // delete user on firebase
            FirebaseAuthService.deleteUser(RegisterController.registrationResponse.getString("idToken"));

            // Switch to register.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(
                    ManageScene.class.getResource("register.fxml")
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



