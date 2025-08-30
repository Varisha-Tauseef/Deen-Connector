package com.example.summer_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PrayerTimings {

    // Location fields
    private double latitude;
    private double longitude;

    // Prayer timings
    private String fajr, sunrise, dhuhr, asr, maghrib, isha, midnight, qiyam;

    public PrayerTimings() {
        getCurrentLocation();
        fetchPrayerTimings();
    }

    /**
     * Fetches the user's approximate latitude and longitude using their IP address.
     */
    private void getCurrentLocation() {
        try {
            URL url = new URL("http://ip-api.com/json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(json.toString());

            this.latitude = (double) data.get("lat");
            this.longitude = (double) data.get("lon");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches prayer timings for the current location using the Aladhan API.
     */
    private void fetchPrayerTimings() {
        try {
            String apiUrl = String.format(
                    "https://api.aladhan.com/v1/timings?latitude=%f&longitude=%f&method=2",
                    latitude, longitude
            );

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            JSONObject data = (JSONObject) json.get("data");
            JSONObject timings = (JSONObject) data.get("timings");

            fajr = (String) timings.get("Fajr");
            sunrise = (String) timings.get("Sunrise");
            dhuhr = (String) timings.get("Dhuhr");
            asr = (String) timings.get("Asr");
            maghrib = (String) timings.get("Maghrib");
            isha = (String) timings.get("Isha");
            midnight = (String) timings.get("Midnight");

            // Calculate Qiyam based on Maghrib and Fajr
            qiyam = calculateQiyamTime(maghrib, fajr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the approximate Qiyam (last third of the night) time
     * by dividing the duration between Maghrib and Fajr.
     *
     * @param maghribTime Maghrib time in HH:mm format
     * @param fajrTime    Fajr time in HH:mm format
     * @return Qiyam time in HH:mm format, or "N/A" if an error occurs
     */
    private String calculateQiyamTime(String maghribTime, String fajrTime) {
        try {
            String[] maghribParts = maghribTime.split(":");
            String[] fajrParts = fajrTime.split(":");

            int maghribMinutes = Integer.parseInt(maghribParts[0]) * 60 + Integer.parseInt(maghribParts[1]);
            int fajrMinutes = Integer.parseInt(fajrParts[0]) * 60 + Integer.parseInt(fajrParts[1]);

            // Handle case where Fajr is after midnight (next day)
            if (fajrMinutes < maghribMinutes) {
                fajrMinutes += 24 * 60;
            }

            int nightDuration = fajrMinutes - maghribMinutes;
            int qiyamStart = maghribMinutes + (2 * nightDuration / 3);

            int qiyamHour = (qiyamStart / 60) % 24;
            int qiyamMinute = qiyamStart % 60;

            return String.format("%02d:%02d", qiyamHour, qiyamMinute);

        } catch (Exception e) {
            return "N/A";
        }
    }

    // Getters
    public String getFajrTiming() { return fajr; }

    public String getSunriseTiming() { return sunrise; }

    public String getDhuhrTiming() { return dhuhr; }

    public String getAsrTiming() { return asr; }

    public String getMaghribTiming() { return maghrib; }

    public String getIshaTiming() { return isha; }

    public String getMidnightTiming() { return midnight; }

    public String getQiyamTiming() { return qiyam; }
}
