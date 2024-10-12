package ua.edu.chmnu.net_dev.c4.tcp.echo.client;

import ua.edu.chmnu.net_dev.c4.tcp.core.client.EndPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Echo {
    private final static int DEFAULT_PORT = 6710;

    public static void main(String[] args) throws IOException {

        EndPoint endPoint;

        if (args.length > 0) {
            endPoint = new EndPoint(args[0]);
        } else {
            endPoint = new EndPoint("localhost", DEFAULT_PORT);
        }

        try(Socket clientSocket = new Socket(endPoint.getHost(), endPoint.getPort())) {

            System.out.println("Establishing connection to " + endPoint.getHost() + ":" + endPoint.getPort());

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

                    System.out.println("Choose an operation: 0 - Average, 1 - Maximum, 2 - Minimum");
                    int choice = scanner.nextInt();
                    writer.println(choice);

                    System.out.println("Enter 5 real numbers:");
                    double[] numbers = new double[5];
                    for (int i = 0; i < 5; i++) {
                        numbers[i] = scanner.nextDouble();
                    }

                    for (double num : numbers) {
                        writer.println(num);
                    }

                    System.out.println("Waiting for response...");

                    String result = reader.readLine();
                    System.out.println("Received result: " + result);

                    System.out.println("Type Q to quit or any other key to continue");
                    String command = scanner.next();
                    if (command.equalsIgnoreCase("Q")) {
                        System.out.println("Done client!");
                        break;
                    }
                }
            }
        }
    }
}