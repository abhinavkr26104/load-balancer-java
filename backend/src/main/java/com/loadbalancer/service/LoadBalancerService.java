package com.loadbalancer.service;

import com.loadbalancer.model.ServerInfo;
import com.loadbalancer.model.TrafficLog;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadBalancerService {
    private final List<ServerInfo> servers = new ArrayList<>();
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    private final Deque<TrafficLog> trafficLogs = new ConcurrentLinkedDeque<>();
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    private static final int MAX_LOGS = 50;

    public LoadBalancerService() {
        servers.add(new ServerInfo("Server-1", "localhost", 9001));
        servers.add(new ServerInfo("Server-2", "localhost", 9002));
        servers.add(new ServerInfo("Server-3", "localhost", 9003));
    }

    public synchronized ServerInfo getNextServer() {
        if (servers.isEmpty()) return null;
        int index = currentIndex.getAndUpdate(i -> (i + 1) % servers.size());
        return servers.get(index);
    }

    public synchronized void recordRequest(ServerInfo server, long latencyMs) {
        server.incrementRequests();
        server.addLatency(latencyMs);
    }

    public void logTraffic(String clientId, String serverId, long latencyMs, boolean success) {
        trafficLogs.addFirst(new TrafficLog(clientId, serverId, latencyMs, success));
        if (trafficLogs.size() > MAX_LOGS) {
            trafficLogs.removeLast();
        }
    }

    public List<ServerInfo> getServers() {
        return new ArrayList<>(servers);
    }

    public List<TrafficLog> getRecentLogs() {
        return new ArrayList<>(trafficLogs);
    }

    public int getActiveConnections() {
        return activeConnections.get();
    }

    public void incrementActiveConnections() {
        activeConnections.incrementAndGet();
    }

    public void decrementActiveConnections() {
        activeConnections.decrementAndGet();
    }

    public long getTotalRequests() {
        return servers.stream().mapToLong(ServerInfo::getRequestCount).sum();
    }
}
