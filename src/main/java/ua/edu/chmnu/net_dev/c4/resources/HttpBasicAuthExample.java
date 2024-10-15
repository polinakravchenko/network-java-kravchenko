package ua.edu.chmnu.net_dev.c4.resources;

import java.io.*;
import java.net.*;
import java.util.Base64;

public class HttpBasicAuthExample {
    public static void main(String[] args) {
        String url = "https://example.com/protected"; // Заміни на потрібний URL
        String username = "user"; // Заміни на свій логін
        String password = "password"; // Заміни на свій пароль

        try {
            // Створюємо об'єкт URL
            URL obj = new URL(url);

            // Відкриваємо з'єднання
            HttpURLConnection httpConn = (HttpURLConnection) obj.openConnection();

            // Додаємо Basic Authorization заголовок з кодуванням Base64
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeaderValue = "Basic " + encodedAuth;
            httpConn.setRequestProperty("Authorization", authHeaderValue);

            // Встановлюємо метод GET
            httpConn.setRequestMethod("GET");

            // Отримуємо код відповіді
            int responseCode = httpConn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Якщо відповідь 200 (OK), читаємо її
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Виводимо відповідь від сервера
                System.out.println("Response: " + response.toString());
            } else {
                // Якщо код відповіді не 200, виводимо повідомлення про помилку
                System.out.println("Failed to connect. HTTP error code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
