package org.room.apollo.server.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpUtil {

    public HttpUtil() {
    }

    /**
     * Shortcut for get request.
     *
     * @param endpoint endpoint.
     * @return response content.
     */
    public String doGet(String endpoint) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return readConnectionResponse(connection);
        } catch (IOException e) {
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Method for read response of connection.
     *
     * @param connection http url connection.
     * @return response content.
     * @throws IOException if it happens.
     */
    private static String readConnectionResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        }
    }
}
