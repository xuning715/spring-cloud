package com.x.spring.cloud.zuul;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.zuul.FilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableHystrix
@EnableCircuitBreaker
//@RefreshScope
public class Application {
    public static void main(String[] args) {
//        FilterProcessor.setProcessor(new DidiFilterProcessor());
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public HystrixCommandAspect hystrixCommandAspect() {
//        return new HystrixCommandAspect();
//    }


//	@Bean
//    public ThrowExceptionFilter throwExceptionFilter() {
//        return new ThrowExceptionFilter();
//    }

//	@Bean
//    public SendErrorFilter sendErrorFilter() {
//        return new SendErrorFilter();
//    }
}