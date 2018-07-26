package com.x.application.config;

import com.x.security.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

@Component
@Configuration
@ImportResource(locations= {"classpath:applicationContext.xml"})
public class Conf {
    @Bean
    public FilterRegistrationBean characterEncodeingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharacterEncodingFilter());
        registration.addInitParameter("encoding", "UTF-8");
        registration.addInitParameter("forceEncoding", "true");
        registration.addUrlPatterns("/*");
        registration.setName("CharacterEncodingFilter");
        registration.setOrder(1);
        return registration;
    }

//    @Bean
//    public FilterRegistrationBean springSessionRepositoryFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy());
//        registration.setName("SpringSessionRepositoryFilter");
//        registration.addUrlPatterns("/*");
//        registration.setOrder(2);
//        return registration;
//    }

//    @Bean
//    public FilterRegistrationBean securityFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setName("SecurityFilter");
//        registration.setFilter(new SecurityFilter());
//        registration.addInitParameter("excludeUrl", "/index.jsp,/common/*,/css/*,/images/*,/js/*");
//        registration.addInitParameter("sessionNullUrl", "/common/exception.jsp");
//        registration.addInitParameter("sessionSecurityUrl", "/common/exception.jsp");
//        registration.addUrlPatterns("/bpm/*");
//        registration.addUrlPatterns("/crm/*");
//        registration.addUrlPatterns("/dc/*");
//        registration.addUrlPatterns("/home/*");
//        registration.setOrder(3);
//        return registration;
//    }

    @Value("${mysql.url}")
    private String host;

    @Value("${mysql.username}")
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
