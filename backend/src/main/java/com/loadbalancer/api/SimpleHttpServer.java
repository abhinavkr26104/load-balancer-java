package com.loadbalancer.api;

import com.loadbalancer.service.LoadBalancerService;
import com.loadbalancer.model.ServerInfo;
import com.loadbalancer.model.TrafficLog;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;

public class SimpleHttpServer {
    private final int port;
    private final LoadBalancerService service;

    public SimpleHttpServer(int port, LoadBalancerService service) {
        this.port = port;
        this.service = service;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/servers", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<ServerInfo> servers = service.getServers();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < servers.size(); i++) {
                    ServerInfo s = servers.get(i);
                    if (i > 0) json.append(",");
                    json.append(String.format(
                        "{\"id\":\"%s\",\"host\":\"%s\",\"port\":%d,\"active\":%b,\"requestCount\":%d,\"avgLatency\":%d}",
                        s.getId(), s.getHost(), s.getPort(), s.isActive(), s.getRequestCount(), Math.round(s.getAvgLatencyMs())
                    ));
                }
                json.append("]");
                sendResponse(exchange, json.toString());
            }
        });

        server.createContext("/stats", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<ServerInfo> servers = service.getServers();
                StringBuilder dist = new StringBuilder("{");
                for (int i = 0; i < servers.size(); i++) {
                    if (i > 0) dist.append(",");
                    dist.append(String.format("\"%s\":%d", servers.get(i).getId(), servers.get(i).getRequestCount()));
                }
                dist.append("}");
                
                String json = String.format(
                    "{\"totalRequests\":%d,\"activeConnections\":%d,\"distribution\":%s}",
                    service.getTotalRequests(), service.getActiveConnections(), dist.toString()
                );
                sendResponse(exchange, json);
            }
        });

        server.createContext("/logs", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<TrafficLog> logs = service.getRecentLogs();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < logs.size(); i++) {
                    TrafficLog log = logs.get(i);
                    if (i > 0) json.append(",");
                    json.append(String.format(
                        "{\"clientId\":\"%s\",\"serverId\":\"%s\",\"latencyMs\":%d,\"timestamp\":\"%s\",\"success\":%b}",
                        log.getClientId(), log.getServerId(), log.getLatencyMs(), log.getTimestamp(), log.isSuccess()
                    ));
                }
                json.append("]");
                sendResponse(exchange, json.toString());
            }
        });

        server.createContext("/connections", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String json = String.format("{\"active\":%d}", service.getActiveConnections());
                sendResponse(exchange, json);
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("HTTP API Server started on port " + port);
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
