package com.example.summer_project;

import org.json.JSONObject;

/**
 * The {@code SessionManager} class handles a user's authentication session,
 * including managing ID tokens, refresh tokens, and automatic token refresh.
 */
public class SessionManager {
    private String idToken = null;
    private String refreshToken = null;
    private long tokenIssuedAt = 0; // in milliseconds

    /**
     * Creates a {@code SessionManager} with the given ID token and refresh token.
     *
     * @param id      the initial ID token
     * @param refresh the initial refresh token
     */
    public SessionManager(String id, String refresh) {
        this.idToken = id;
        this.refreshToken = refresh;
        tokenIssuedAt = System.currentTimeMillis();
    }

    /**
     * Returns the current ID token. If the token is older than 55 minutes, it
     * automatically refreshes it using the refresh token.
     *
     * @return the current ID token
     */
    public String getIdToken() {
        if (System.currentTimeMillis() - tokenIssuedAt >= 55 * 60 * 1000) {
            JSONObject response = FirebaseAuthService.refreshIdToken(getRefreshToken());
            updateIdToken(response.getString("id_token"));
            updateRefreshToken(response.getString("refresh_token"));
        }
        return idToken;
    }

    /**
     * Updates the current ID token and resets the token issuance time.
     *
     * @param newIdToken the new ID token
     */
    public void updateIdToken(String newIdToken) {
        idToken = newIdToken;
        tokenIssuedAt = System.currentTimeMillis();
    }

    /**
     * Updates the refresh token.
     *
     * @param newRefreshToken the new refresh token
     */
    public void updateRefreshToken(String newRefreshToken) {
        refreshToken = newRefreshToken;
    }

    /**
     * Returns the current refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Resets the session by clearing the ID token, refresh token, and token issuance time.
     */
    public void resetSession() {
        idToken = null;
        refreshToken = null;
        tokenIssuedAt = 0;
    }
}
