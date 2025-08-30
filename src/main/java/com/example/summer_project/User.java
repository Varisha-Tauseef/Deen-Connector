package com.example.summer_project;

/**
 * The {@code User} class represents a user account with an email, username, and unique local ID.
 */
public class User {
    private String email;
    private String username;
    private String localId;

    /**
     * Constructs a {@code User} with the specified email and local ID.
     *
     * @param email   the user's email address
     * @param localId the user's unique local ID
     */
    public User(String email, String localId) {
        this.email = email;
        this.localId = localId;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's unique local ID.
     *
     * @return the local ID
     */
    public String getLocalId() {
        return localId;
    }
}
