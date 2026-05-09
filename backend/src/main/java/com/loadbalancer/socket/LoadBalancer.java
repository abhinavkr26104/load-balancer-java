package com.loadbalancer.socket;

import com.loadbalancer.service.LoadBalancerService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancer {
    private final int port;
    private final LoadBalancerService service;
    private final ExecutorService executorService;
    private volatile boolean running = true;
    private final AtomicInteger clientCounter = new AtomicInteger(0);

    public LoadBalancer(int port, LoadBalancerService service) {
        this.port = port;
        this.service = service;
        this.executorService = Executors.newFixedThreadPool(20);
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Load Balancer started on port " + port);
                while (running) {
                    Socket clientSocket = serverSocket.accept();
                    String clientId = "Client-" + clientCounter.incrementAndGet();
                    service.incrementActiveConnections();
                    executorService.submit(() -> {
                        new ClientHandler(clientSocket, service, clientId).run();
                        service.decrementActiveConnections();
                    });
                }
            } catch (IOException e) {
                System.err.println("Load Balancer error: " + e.getMessage());
            }
        }).start();
    }

    public void stop() {
        running = false;
        executorService.shutdown();
    }
}
