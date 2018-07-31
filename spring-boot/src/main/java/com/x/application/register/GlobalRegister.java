package com.x.application.register;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.x.security.filter.JWTFilter;
import com.x.security.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@ImportResource(locations= {"classpath:applicationContext.xml"})
public class GlobalRegister {

    @Bean
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet() {
        return new HystrixMetricsStreamServlet();
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CorsFilter(configSource));
        registrationBean.setEnabled(true);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

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

    @Bean
    public FilterRegistrationBean securityFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("SecurityFilter");
        registration.setFilter(new SecurityFilter());
        registration.addInitParameter("excludeUrl", "/index.jsp,/common/*,/css/*,/images/*,/js/*");
        registration.addInitParameter("sessionNullUrl", "/common/exception.jsp");
        registration.addInitParameter("sessionSecurityUrl", "/common/exception.jsp");
        registration.addUrlPatterns("/bpm/*");
        registration.addUrlPatterns("/crm/*");
        registration.addUrlPatterns("/dc/*");
        registration.addUrlPatterns("/home/*");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("JWTFilter");
        registration.setFilter(new JWTFilter());
        registration.addUrlPatterns("/controller/token/*");
        registration.setOrder(4);
        return registration;
    }

    @Bean
    public ServletRegistrationBean hystrixStreamServletRegistration(HystrixMetricsStreamServlet servlet) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(servlet);
        registrationBean.setEnabled(true);//是否启用该registrationBean
        registrationBean.addUrlMappings("/hystrix.stream", "/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        registrationBean.setOrder(2);
        return registrationBean;
    }

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
