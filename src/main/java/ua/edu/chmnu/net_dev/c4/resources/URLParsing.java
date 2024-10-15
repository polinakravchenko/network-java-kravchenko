package ua.edu.chmnu.net_dev.c4.resources;

import java.net.URL;
import java.util.Scanner;

public class URLParsing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a URL: ");
        String urlString = scanner.nextLine();

        try {

            URL url = new URL(urlString);

            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());

            int port = url.getPort();
            if (port == -1) {
                if (url.getProtocol().equals("http")) {
                    port = 80;
                } else if (url.getProtocol().equals("https")) {
                    port = 443;
                }
                System.out.println("Port: Not specified, using default port " + port);
            } else {
                System.out.println("Port: " + port);
            }

            System.out.println("Path: " + url.getPath());

            String query = url.getQuery();
            if (query != null) {
                System.out.println("Query: " + query);
            } else {
                System.out.println("Query: Not specified");
            }

            String fragment = url.getRef();
            if (fragment != null) {
                System.out.println("Fragment: " + fragment);
            } else {
                System.out.println("Fragment: Not specified");
            }

        } catch (Exception e) {
            System.out.println("Invalid URL format.");
            e.printStackTrace();
        }
    }
}