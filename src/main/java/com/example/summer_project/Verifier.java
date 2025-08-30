package com.example.summer_project;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for verifying passwords and Gmail addresses.
 */
public class Verifier {

    /**
     * Verifies whether the given password meets security requirements.
     *
     * @param password the password string to verify
     * @return true if the password meets all requirements; false otherwise
     */
    public static boolean verifyPassword(String password) {
        if (password.length() < 8) {
            showAlert("Password must be at least 8 characters long.");
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            showAlert("Password must contain at least one uppercase letter.");
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            showAlert("Password must contain at least one lowercase letter.");
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            showAlert("Password must contain at least one digit.");
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()\\-_=+<>?].*")) {
            showAlert("Password must contain at least one special character (!@#$%^&*()-_+=<>?).");
            return false;
        }
        return true;
    }

    /**
     * Displays an error alert with the specified message.
     *
     * @param message the message to display in the alert
     */
    private static void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Password Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Verifies whether the given email address is a valid Gmail address.
     *
     * @param email the email address to verify
     * @return true if the email is a valid Gmail address; false otherwise
     */
    public static boolean verifyGmail(String email) {
        if (email == null || !email.toLowerCase().matches("^[\\w.+-]+@gmail\\.com$")) {
            showAlert("Please enter a valid Gmail address (must end with @gmail.com).");
            return false;
        }
        return true;
    }
}
