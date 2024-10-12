package ua.edu.chmnu.net_dev.c4.tcp.echo.server.mt;

import ua.edu.chmnu.net_dev.c4.tcp.core.server.ClientSession;
import ua.edu.chmnu.net_dev.c4.tcp.core.server.ServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClientSession implements ClientSession {

    private final Socket socket;

    public EchoClientSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void process() {
        try (var socket = this.socket) {
            try (var ir = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 var writer = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Establishing connection from: " + socket.getRemoteSocketAddress());

                String promptNick = "Enter your nick:";
                writer.println(promptNick);
                String nick = ir.readLine();
                System.out.println("Client nick: " + nick);

                while (!socket.isClosed()) {
                    try {
                        System.out.println("Waiting for operation choice from: " + nick);

                        // Read operation choice
                        String choiceStr = ir.readLine();
                        if (choiceStr == null || choiceStr.isBlank()) break;
                        int choice = Integer.parseInt(choiceStr);

                        // Read 5 real numbers
                        double[] numbers = new double[5];
                        for (int i = 0; i < 5; i++) {
                            numbers[i] = Double.parseDouble(ir.readLine());
                        }

                        // Perform the operation based on the client's choice
                        double result;
                        switch (choice) {
                            case 0: // Average
                                result = calculateAverage(numbers);
                                break;
                            case 1: // Maximum
                                result = findMaximum(numbers);
                                break;
                            case 2: // Minimum
                                result = findMinimum(numbers);
                                break;
                            default:
                                writer.println("Invalid operation choice.");
                                continue;
                        }

                        // Send the result back to the client
                        writer.println("Result: " + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }

                System.out.println("Client " + nick + " disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    private double calculateAverage(double[] numbers) {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.length;
    }

    private double findMaximum(double[] numbers) {
        double max = numbers[0];
        for (double num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private double findMinimum(double[] numbers) {
        double min = numbers[0];
        for (double num : numbers) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }
}