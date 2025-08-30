package com.example.summer_project;

import org.json.JSONObject;

/**
 * The {@code Settings} class provides methods to manage a user's account settings,
 * including changing email, password, username, logging out, and deleting the account.
 */
public class Settings {
    private User user;
    private SessionManager session;

    /**
     * Creates a {@code Settings} instance for the given user and session.
     *
     * @param user    the user whose settings are being managed
     * @param session the session associated with the user
     */
    public Settings(User user, SessionManager session) {
        this.user = user;
        this.session = session;
    }

    /**
     * Sends a verification email to change the user's email address.
     *
     * @param newEmail the new email address to set
     */
    public void changeEmail(String newEmail) {
        JSONObject result = FirebaseAuthService.sendChangeEmailVerification(session.getIdToken(), newEmail);

        if (result.has("error")) {
            System.out.println("Failed to send email change verification: " + result);
        } else {
            System.out.println("Verification email sent to " + newEmail);
        }
    }

    /**
     * Sends a password reset email to the user's current email.
     */
    public void changePassword() {
        FirebaseAuthService.sendPasswordResetEmail(user.getEmail());
    }

    /**
     * Changes the user's username and updates it in the Firebase database.
     *
     * @param username the new username to set
     */
    public void changeUsername(String username) {
        user.setUsername(username);

        JSONObject data = FirebaseDatabaseService.createUserDataJson(username);
        FirebaseDatabaseService.storeUserData(session.getIdToken(), user.getLocalId(), data);
    }

    /**
     * Logs out the user, storing current user data and resetting the session.
     */
    public void logout() {
        JSONObject userData = FirebaseDatabaseService.createUserDataJson(user.getUsername());
        FirebaseDatabaseService.storeUserData(session.getIdToken(), user.getLocalId(), userData);
        user = null;
        session.resetSession();
    }

    /**
     * Deletes the user's account and resets the session.
     */
    public void deleteAccount() {
        FirebaseAuthService.deleteUser(session.getIdToken());
        user = null;
        session.resetSession();
    }
}
