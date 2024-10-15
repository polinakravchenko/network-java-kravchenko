package ua.edu.chmnu.net_dev.c4.resources;

import java.io.*;
import java.net.*;

public class CookieHandlerExample {
    public static void main(String[] args) {

        String initialUrl = "https://example.com/login";
        String subsequentUrl = "https://example.com/home";

        try {
            URL url = new URL(initialUrl);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            String cookies = httpConn.getHeaderField("Set-Cookie");
            System.out.println("Initial Cookies: " + cookies);

            url = new URL(subsequentUrl);

            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Cookie", cookies);

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
