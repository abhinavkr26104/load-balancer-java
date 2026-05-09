package com.loadbalancer;

import java.io.*;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        System.out.println("Starting test client - sending 1000 requests to load balancer...\n");
        
        for (int i = 1; i <= 1000; i++) {
            final int requestNum = i;
            new Thread(() -> {
                try {
                    Thread.sleep((long)(Math.random() * 1000));
                    sendRequest("Request-" + requestNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void sendRequest(String message) {
        try (Socket socket = new Socket("localhost", 8000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(message);
            String response = in.readLine();
            System.out.println("Sent: " + message + " | Received: " + response);

        } catch (IOException e) {
            System.err.println("Error sending request: " + e.getMessage());
        }
    }
}
