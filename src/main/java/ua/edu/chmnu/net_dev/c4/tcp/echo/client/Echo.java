package ua.edu.chmnu.net_dev.c4.tcp.echo.client;

import ua.edu.chmnu.net_dev.c4.tcp.core.client.EndPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Echo {
    private final static int DEFAULT_PORT = 6710;

    public static String generateRandomString(int length) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public static void main(String[] args) throws IOException {

        EndPoint endPoint;

        if (args.length > 0) {
            endPoint = new EndPoint(args[0]);
        } else {
            endPoint = new EndPoint("localhost", DEFAULT_PORT);
        }

        try(Socket clientSocket = new Socket(endPoint.getHost(), endPoint.getPort())) {

            System.out.println("Establish connection to " + endPoint.getHost() + ":" + endPoint.getPort());

            try (
                    var scanner = new Scanner(System.in);
                    var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {

                String promptNick = reader.readLine();
                System.out.print(promptNick);

                var nick = scanner.nextLine();
                writer.println(nick);

                while (!clientSocket.isClosed()) {

                    String randomString = generateRandomString(15);
                    System.out.println("Generated random string: " + randomString);

                    writer.println(randomString);

                    System.out.println("Waiting for response...");
                    String response = reader.readLine();
                    System.out.println("Received response: " + response);

                    System.out.print("Enter 'Q' to quit or press Enter to send another string: ");
                    String command = scanner.nextLine();
                    if (command.equalsIgnoreCase("Q")) {
                        System.out.println("Client session ended.");
                        break;
                    }
                }
            }
        }
    }
}