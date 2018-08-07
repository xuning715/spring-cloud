package com.x.application;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableCircuitBreaker
@PropertySource("file:application.properties")
@EnableDubboConfiguration
@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60, redisNamespace = "consumer")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}