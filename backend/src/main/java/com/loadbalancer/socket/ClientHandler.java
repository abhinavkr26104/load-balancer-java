package com.loadbalancer.socket;

import com.loadbalancer.model.ServerInfo;
import com.loadbalancer.service.LoadBalancerService;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final LoadBalancerService service;
    private final String clientId;

    public ClientHandler(Socket clientSocket, LoadBalancerService service, String clientId) {
        this.clientSocket = clientSocket;
        this.service = service;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            ServerInfo server = service.getNextServer();
            
            if (server == null) {
                out.println("ERROR: No available servers");
                service.logTraffic(clientId, "NONE", 0, false);
                return;
            }

            long startTime = System.currentTimeMillis();
            String response = forwardToServer(server, request);
            long latency = System.currentTimeMillis() - startTime;

            out.println(response);
            service.recordRequest(server, latency);
            service.logTraffic(clientId, server.getId(), latency, true);

        } catch (Exception e) {
            System.err.println("Client handler error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String forwardToServer(ServerInfo server, String request) {
        try (Socket serverSocket = new Socket(server.getHost(), server.getPort());
             PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            out.println(request);
            return in.readLine();

        } catch (IOException e) {
            return "ERROR: Server " + server.getId() + " unavailable";
        }
    }
}
