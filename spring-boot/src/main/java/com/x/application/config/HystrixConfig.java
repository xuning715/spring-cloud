package com.x.application.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfig {
    @Bean
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet() {
        return new HystrixMetricsStreamServlet();
    }

    @Bean
    public ServletRegistrationBean registration(HystrixMetricsStreamServlet servlet) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(servlet);
        registrationBean.setEnabled(true);//是否启用该registrationBean
        registrationBean.addUrlMappings("/hystrix.stream", "/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
