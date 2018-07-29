package com.x.application;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableCircuitBreaker
@EnableDubboConfiguration
@Configuration
@PropertySource("file:application.properties")
//@PropertySource("classpath:application.properties")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public HystrixCommandAspect hystrixCommandAspect() {
//        return new HystrixCommandAspect();
//    }

//    @Bean
//    public RemoteIpFilter remoteIpFilter() {
//        return new RemoteIpFilter();
//    }

//    @Bean
//    public ServletRegistrationBean dwrServletRegistrationBean() {
//        ServletRegistrationBean registration = new ServletRegistrationBean();
//        registration.setName("dwr-invoker");
//        registration.setServlet(new DwrSpringServlet());
//        registration.addInitParameter("fileUploadMaxBytes", "25000");
//        registration.addInitParameter("debug", "false");
//        registration.addInitParameter("accessLogLevel", "EXCEPTION");
//        registration.addInitParameter("allowScriptTagRemoting", "true");
//        registration.addInitParameter("crossDomainSessionSecurity", "false");
//        registration.addInitParameter("allowGetForSafariButMakeForgeryEasier", "true");
//        registration.addInitParameter("activeReverseAjaxEnabled", "true");
//        registration.addInitParameter("initApplicationScopeCreatorsAtStartup", "true");
//        registration.addInitParameter("jsonRpcEnabled", "true");
//        registration.addInitParameter("jsonpEnabled", "true");
//        registration.addInitParameter("preferDataUrlSchema", "false");
//        registration.addUrlMappings("/dwr/*");
//        registration.setOrder(10);
//        return registration;
//    }

//    @Bean
//    public ServletListenerRegistrationBean<ContextLoaderListener> contextLoaderListenerRegistrationBean() {
//        ServletListenerRegistrationBean registration = new ServletListenerRegistrationBean();
//        registration.setListener(new ContextLoaderListener());
//        return registration;
//    }

//    @Bean
//    public ServletListenerRegistrationBean<DwrListener> dwrListenerRegistrationBean() {
//        ServletListenerRegistrationBean registration = new ServletListenerRegistrationBean();
//        registration.setListener(new DwrListener());
//        return registration;
//    }

}