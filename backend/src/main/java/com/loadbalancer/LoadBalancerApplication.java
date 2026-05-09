package com.loadbalancer;

import com.loadbalancer.service.LoadBalancerService;
import com.loadbalancer.socket.BackendServer;
import com.loadbalancer.socket.LoadBalancer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoadBalancerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerApplication.class, args);
    }

    @Bean
    public CommandLineRunner startServers(LoadBalancerService service) {
        return args -> {
            new BackendServer("Server-1", 9001).start();
            new BackendServer("Server-2", 9002).start();
            new BackendServer("Server-3", 9003).start();
            
            Thread.sleep(1000);
            
            new LoadBalancer(8000, service).start();
            
            System.out.println("\n===========================================");
            System.out.println("Network Load Balancer System Started");
            System.out.println("===========================================");
            System.out.println("Backend Servers: 9001, 9002, 9003");
            System.out.println("Load Balancer: 8000");
            System.out.println("REST API: http://localhost:8080");
            System.out.println("Dashboard: Open frontend/index.html");
            System.out.println("===========================================\n");
        };
    }
}
