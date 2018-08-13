package com.x.spring.cloud.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
@PropertySource("file:application.properties")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}