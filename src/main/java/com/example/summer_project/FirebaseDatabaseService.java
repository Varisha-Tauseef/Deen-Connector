package com.example.summer_project;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class FirebaseDatabaseService {
    private static final String DATABASE_URL = Config.get("databaseURL");

    /**
     * Stores user data in Firebase Realtime Database under /users/{userId}.json
     *
     * @param idToken Firebase auth idToken for authentication
     * @param userId Firebase UID (localId)
     * @param userData JSONObject containing user fields to store
     * @return JSONObject with the response or error
     */
    public static JSONObject storeUserData(String idToken, String userId, JSONObject userData) {
        try {
            String urlString = DATABASE_URL + "users/" + userId + ".json?auth=" + idToken;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");  // Use PUT to overwrite or create
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(userData.toString().getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
            return errorJson;
        }
    }

    /**
     * Loads user data from Firebase Realtime Database at /users/{userId}.json
     *
     * @param idToken Firebase auth idToken for authentication
     * @param userId Firebase UID (localId)
     * @return JSONObject with user data or error
     */
    public static JSONObject loadUserData(String idToken, String userId) {
        try {
            String urlString = DATABASE_URL + "users/" + userId + ".json?auth=" + idToken;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            InputStream responseStream = (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
            return errorJson;
        }
    }

    /**
     * Creates a JSONObject from given user fields to store in Firebase.
     *
     * @param username User's username
     * @return JSONObject containing all provided user fields
     */
    public static JSONObject createUserDataJson(String username) {
        JSONObject json = new JSONObject();
        json.put("username", username);
        return json;
    }
}

