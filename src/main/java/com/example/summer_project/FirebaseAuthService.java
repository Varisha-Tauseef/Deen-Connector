package com.example.summer_project;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.example.summer_project.main.session;

public class FirebaseAuthService {
    private static final String API_KEY = Config.get("firebaseAuthAPIKey");
    private static final String SIGNUP_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;
    private static final String SIGNIN_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;

    public static JSONObject registerUser(String email, String password) {
        try {
            URL url = new URL(SIGNUP_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String payload = String.format(
                    "{\"email\":\"%s\", \"password\":\"%s\", \"returnSecureToken\":true}",
                    email, password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = (conn.getResponseCode() == 200)
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

    public static JSONObject signIn(String email, String password) {
        try {
            URL url = new URL(SIGNIN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String payload = String.format(
                    "{\"email\":\"%s\", \"password\":\"%s\", \"returnSecureToken\":true}",
                    email, password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = (conn.getResponseCode() == 200)
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

    public static JSONObject refreshIdToken(String refreshToken) {
        try {
            URL url = new URL("https://securetoken.googleapis.com/v1/token?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String payload = "grant_type=refresh_token&refresh_token=" + refreshToken;
            conn.getOutputStream().write(payload.getBytes(StandardCharsets.UTF_8));

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // Return the entire JSON response
            return new JSONObject(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", e.getMessage());
            return errorJson;
        }
    }

    public static JSONObject deleteUser(String idToken) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:delete?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String payload = String.format("{\"idToken\":\"%s\"}", idToken);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = (conn.getResponseCode() == 200)
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

    public static JSONObject sendPasswordResetEmail(String email) {
        try {
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            String payload = String.format(
                    "{\"requestType\":\"PASSWORD_RESET\", \"email\":\"%s\"}",
                    email);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            InputStream responseStream = (conn.getResponseCode() == 200)
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

    private static JSONObject sendRequest(String urlStr, String payload, String contentType) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        InputStream responseStream = (conn.getResponseCode() == 200)
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
    }

    public static JSONObject sendChangeEmailVerification(String idToken, String newEmail) {
        try {
            String payload = String.format(
                    "{\"requestType\":\"VERIFY_AND_CHANGE_EMAIL\", \"idToken\":\"%s\", \"newEmail\":\"%s\"}",
                    idToken, newEmail
            );
            return sendRequest("https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=" + API_KEY, payload, "application/json; charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject().put("error", e.getMessage());
        }
    }
}
