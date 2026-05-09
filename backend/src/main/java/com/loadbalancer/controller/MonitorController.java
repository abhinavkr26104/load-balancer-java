package com.loadbalancer.controller;

import com.loadbalancer.service.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class MonitorController {

    @Autowired
    private LoadBalancerService service;

    @GetMapping("/servers")
    public List<Map<String, Object>> getServers() {
        List<Map<String, Object>> result = new ArrayList<>();
        service.getServers().forEach(server -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", server.getId());
            map.put("host", server.getHost());
            map.put("port", server.getPort());
            map.put("active", server.isActive());
            map.put("requestCount", server.getRequestCount());
            map.put("avgLatency", Math.round(server.getAvgLatencyMs()));
            result.add(map);
        });
        return result;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRequests", service.getTotalRequests());
        stats.put("activeConnections", service.getActiveConnections());
        
        Map<String, Long> distribution = new HashMap<>();
        service.getServers().forEach(s -> distribution.put(s.getId(), s.getRequestCount()));
        stats.put("distribution", distribution);
        
        return stats;
    }

    @GetMapping("/logs")
    public List<Map<String, Object>> getLogs() {
        List<Map<String, Object>> result = new ArrayList<>();
        service.getRecentLogs().forEach(log -> {
            Map<String, Object> map = new HashMap<>();
            map.put("clientId", log.getClientId());
            map.put("serverId", log.getServerId());
            map.put("latencyMs", log.getLatencyMs());
            map.put("timestamp", log.getTimestamp());
            map.put("success", log.isSuccess());
            result.add(map);
        });
        return result;
    }

    @GetMapping("/connections")
    public Map<String, Integer> getConnections() {
        return Map.of("active", service.getActiveConnections());
    }
}
