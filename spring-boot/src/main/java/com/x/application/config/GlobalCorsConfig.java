package com.x.application.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class GlobalCorsConfig  {
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        config.addExposedHeader("*");
//
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(configSource));
//        registrationBean.setOrder(0);
//        return registrationBean;
//    }
}
