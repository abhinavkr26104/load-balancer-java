package com.loadbalancer;

import com.loadbalancer.service.LoadBalancerService;
import com.loadbalancer.socket.BackendServer;
import com.loadbalancer.socket.LoadBalancer;
import com.loadbalancer.api.SimpleHttpServer;

public class StandaloneApp {
    public static void main(String[] args) throws Exception {
        LoadBalancerService service = new LoadBalancerService();
        
        // Start backend servers
        new BackendServer("Server-1", 9001).start();
        new BackendServer("Server-2", 9002).start();
        new BackendServer("Server-3", 9003).start();
        
        Thread.sleep(1000);
        
        // Start load balancer
        new LoadBalancer(8000, service).start();
        
        // Start simple HTTP server for API
        new SimpleHttpServer(8080, service).start();
        
        System.out.println("\n===========================================");
        System.out.println("Network Load Balancer System Started");
        System.out.println("===========================================");
        System.out.println("Backend Servers: 9001, 9002, 9003");
        System.out.println("Load Balancer: 8000");
        System.out.println("REST API: http://localhost:8080");
        System.out.println("Dashboard: Open frontend/index.html");
        System.out.println("===========================================\n");
    }
}
