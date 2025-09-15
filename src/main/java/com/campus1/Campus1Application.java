package com.campus1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAsync
@EnableAdminServer   // ✅ enable Spring Boot Admin Server
@SpringBootApplication
public class Campus1Application {

    public static void main(String[] args) {
        SpringApplication.run(Campus1Application.class, args);
    }
}
