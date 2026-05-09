package com.loadbalancer.socket;

import java.io.*;
import java.net.*;

public class BackendServer {
    private final String id;
    private final int port;
    private volatile boolean running = true;

    public BackendServer(String id, int port) {
        this.id = id;
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println(id + " started on port " + port);
                while (running) {
                    Socket clientSocket = serverSocket.accept();
                    handleRequest(clientSocket);
                }
            } catch (IOException e) {
                System.err.println(id + " error: " + e.getMessage());
            }
        }).start();
    }

    private void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            String request = in.readLine();
            Thread.sleep(100 + (long)(Math.random() * 200));
            out.println("Response from " + id + " | Request: " + request);
            
        } catch (Exception e) {
            System.err.println(id + " request error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
