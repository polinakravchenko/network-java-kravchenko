package ua.edu.chmnu.net_dev.c4.resources;

import java.io.*;
import java.net.*;

public class FollowRedirectExample {
    public static void main(String[] args) {
        String initialUrl = "https://httpstat.us/301";
        try {
            URL url = new URL(initialUrl);
            HttpURLConnection httpConn = (HttpURLConnection)
                    url.openConnection();
            httpConn.setInstanceFollowRedirects(false); // Disable auto-follow
            int responseCode = httpConn.getResponseCode();
            while (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode
                    == HttpURLConnection.HTTP_SEE_OTHER) {
                String redirectUrl =
                        httpConn.getHeaderField("Location");
                System.out.println("Redirected to: " + redirectUrl);
                url = new URL(redirectUrl);
                httpConn = (HttpURLConnection) url.openConnection();
                responseCode = httpConn.getResponseCode();
            }
// Read final response
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(httpConn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Final URL: " + url);
            System.out.println("Content: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
