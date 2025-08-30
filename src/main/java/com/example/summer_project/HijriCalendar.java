package com.example.summer_project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HijriCalendar {

    private static final String BASE_URL = "https://api.aladhan.com/v1/gToHCalendar/";

    /**
     * Fetches Islamic events for the given Gregorian month and year.
     *
     * @param month Gregorian month (1–12)
     * @param year  Gregorian year
     * @return Map with LocalDate as key and event name as value
     */
    public static Map<LocalDate, String> getIslamicEvents(int month, int year) {
        Map<LocalDate, String> events = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            String urlString = BASE_URL + month + "/" + year;
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

            JSONObject jsonResponse = new JSONObject(response.toString());

            if (jsonResponse.getInt("code") == 200) {
                JSONArray days = jsonResponse.getJSONArray("data");

                for (int i = 0; i < days.length(); i++) {
                    JSONObject dayObj = days.getJSONObject(i);
                    JSONObject hijri = dayObj.getJSONObject("hijri");

                    JSONArray holidays = hijri.getJSONArray("holidays");

                    if (holidays.length() > 0) {
                        String gregorianDateStr = dayObj.getJSONObject("gregorian").getString("date");
                        LocalDate gregorianDate = LocalDate.parse(gregorianDateStr, formatter);

                        for (int h = 0; h < holidays.length(); h++) {
                            events.put(gregorianDate, holidays.getString(h));
                        }
                    }
                }
            } else {
                System.err.println("API Error: " + jsonResponse.optString("status", "Unknown error"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}

