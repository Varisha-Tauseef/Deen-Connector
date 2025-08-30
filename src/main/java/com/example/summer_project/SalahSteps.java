package com.example.summer_project;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The {@code SalahSteps} class provides functionality to send images and position data
 * to a remote API for classifying Salah steps.
 */
public class SalahSteps {
    public static final String SalahStepsAPIURL = Config.get("SalahStepsAPIURL") + "/classify";

    /**
     * Sends an image file along with the current Salah position to the classification API.
     *
     * @param filePath the local file path of the image to be sent
     * @param position the name of the current Salah position
     * @return a {@link JSONObject} containing the API response, or {@code null} if an error occurs
     */
    public static JSONObject sendToAPI(String filePath, String position) {
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        String LINE_FEED = "\r\n";

        try {
            URL url = new URL(SalahStepsAPIURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {

                // Position field
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"position\"").append(LINE_FEED);
                writer.append(LINE_FEED).append(position).append(LINE_FEED);
                writer.flush();

                // Image field
                File file = new File(filePath);
                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + file.getName() + "\"").append(LINE_FEED);
                writer.append("Content-Type: image/jpeg").append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.flush();

                try (FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }

                writer.append(LINE_FEED).flush();
                writer.append("--" + boundary + "--").append(LINE_FEED);
                writer.flush();
            }

            // Get response
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode == HttpURLConnection.HTTP_OK) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseStr.append(line);
            }
            in.close();

            return new JSONObject(responseStr.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
