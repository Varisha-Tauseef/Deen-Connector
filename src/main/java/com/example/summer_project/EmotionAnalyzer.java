package com.example.summer_project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class EmotionAnalyzer {
    /**
     * Retrieves a random dua or Quranic verse from the local response.json
     * for the given subcategory.
     *
     * @param subcategory The emotion subcategory.
     * @return A JSONObject containing the selected dua or verse, or null if not found.
     */
    public static JSONObject getRandomDuaOrVerse(String subcategory) {
        try (InputStream is = EmotionAnalyzer.class.getResourceAsStream("/response.json")) {

            if (is == null) {
                System.err.println("response.json not found in resources!");
                return null;
            }

            // Read entire JSON file as String
            String jsonText = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonText);

            // Check if subcategory exists in JSON
            if (!json.has(subcategory)) {
                System.err.println("Subcategory not found in JSON: " + subcategory);
                return null;
            }

            JSONArray array = json.getJSONArray(subcategory);
            if (array.isEmpty()) {
                System.err.println("No entries in subcategory: " + subcategory);
                return null;
            }

            // Return a random entry
            Random rand = new Random();
            return array.getJSONObject(rand.nextInt(array.length()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends user input text to the backend API to detect emotion subcategory.
     *
     * @param text User input text
     * @return Detected subcategory or null if API fails
     */
    public static String detectEmotion(String text) {
        HttpURLConnection conn = null;
        try {
            String apiUrl = Config.get("emotionClassifierAPI");
            URL url = new URL(apiUrl + "/predict");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Prepare JSON payload
            String jsonInput = "{\"text\":\"" + text + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            // Check API response code
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                System.err.println("Backend returned status: " + status);
                return null;
            }

            // Read API response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.optString("subcategory", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) conn.disconnect();
        }
    }
}
