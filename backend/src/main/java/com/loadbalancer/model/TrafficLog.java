package com.loadbalancer.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TrafficLog {
    private String clientId;
    private String serverId;
    private long latencyMs;
    private String timestamp;
    private boolean success;

    public TrafficLog(String clientId, String serverId, long latencyMs, boolean success) {
        this.clientId = clientId;
        this.serverId = serverId;
        this.latencyMs = latencyMs;
        this.success = success;
        this.timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getClientId() { return clientId; }
    public String getServerId() { return serverId; }
    public long getLatencyMs() { return latencyMs; }
    public String getTimestamp() { return timestamp; }
    public boolean isSuccess() { return success; }
}
