package ua.edu.chmnu.net_dev.c4.resources;

import java.io.*;
import java.net.*;

public class FileUploadExample {
    public static void main(String[] args) {

        String url = "https://example.com/upload";
        String filePath = "C:/path/to/file.txt";

        try {

            File file = new File(filePath);
            String boundary = "===" + System.currentTimeMillis() + "===";
            URL obj = new URL(url);

            HttpURLConnection httpConn = (HttpURLConnection)obj.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setDoOutput(true);

            try (OutputStream outputStream =
                         httpConn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true))
            {
                writer.append("--" + boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append("\r\n");
                writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName())).append("\r\n");
                writer.append("Content-Transfer-Encoding:binary").append("\r\n");
                writer.append("\r\n").flush();

                FileInputStream inputStream = new
                        FileInputStream(file);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1)
                {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush();
                inputStream.close();

                writer.append("\r\n").flush();
                writer.append("--" + boundary + "--").append("\r\n");

                writer.close();
            }

            int responseCode = httpConn.getResponseCode();

            System.out.println("POST Response Code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response: " + response.toString());
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
