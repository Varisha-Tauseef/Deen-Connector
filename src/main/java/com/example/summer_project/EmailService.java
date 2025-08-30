package com.example.summer_project;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Random;
import javafx.scene.control.Alert;

public class EmailService {
    /**
     * Sends a 2FA (Two-Factor Authentication) code to the specified email address.
     * Uses Gmail SMTP with authentication and TLS.
     *
     * @param recipientEmail The recipient's email address
     * @param code           The verification code to send
     */
    public static void send2FACode(String recipientEmail, String code) {
        final String senderEmail = Config.get("senderEmail");
        final String senderPassword = Config.get("senderPassword");

        // SMTP server properties for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS (TLS encryption)
        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        props.put("mail.smtp.port", "587"); // TLS port

        // Create a mail session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Prepare email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail)); // Set sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Set recipient
            message.setSubject("Your 2FA Code"); // Email subject
            message.setText("Your 2FA verification code is: " + code); // Email body text

            // Send the email
            Transport.send(message);

            // Show success confirmation alert in JavaFX
            showAlert(Alert.AlertType.INFORMATION, "Success", "Email Sent", "A verification code has been sent to your email.");

        } catch (MessagingException e) {
            // Show error alert to the user if email fails to send
            showAlert(Alert.AlertType.ERROR, "Email Error", "Failed to send email", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Generates a random 6-digit numeric code for 2FA verification.
     *
     * @return Random 6-digit integer code between 100000 and 999999
     */
    public static int codeGenerator() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * Helper method to show a JavaFX alert dialog.
     *
     * @param type    The type of alert
     * @param title   The title of the alert window
     * @param header  The header text of the alert
     * @param message The main content message of the alert
     */
    private static void showAlert(Alert.AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
