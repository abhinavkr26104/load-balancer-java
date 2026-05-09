package com.loadbalancer.model;

public class ServerInfo {
    private String id;
    private String host;
    private int port;
    private boolean active;
    private long requestCount;
    private long totalLatencyMs;

    public ServerInfo(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.active = true;
        this.requestCount = 0;
        this.totalLatencyMs = 0;
    }

    public String getId() { return id; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public long getRequestCount() { return requestCount; }
    public void incrementRequests() { this.requestCount++; }
    public long getTotalLatencyMs() { return totalLatencyMs; }
    public void addLatency(long ms) { this.totalLatencyMs += ms; }

    public double getAvgLatencyMs() {
        return requestCount == 0 ? 0 : (double) totalLatencyMs / requestCount;
    }
}
