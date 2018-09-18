package com.x.application.register;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ImportResource(locations= {"classpath:applicationContext.xml"})
public class GlobalRegister {

    @Bean
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet() {
        return new HystrixMetricsStreamServlet();
    }

//    @Bean
//    public FilterRegistrationBean corsFilterRegistration() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedHeader(CorsConfiguration.ALL);
//        config.addAllowedMethod(CorsConfiguration.ALL);
//        config.addAllowedOrigin(CorsConfiguration.ALL);
//        config.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new CorsFilter(configSource));
//        registrationBean.setEnabled(true);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean characterEncodeingFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CharacterEncodingFilter());
//        registration.addInitParameter("encoding", "UTF-8");
//        registration.addInitParameter("forceEncoding", "true");
//        registration.addUrlPatterns("/*");
//        registration.setName("CharacterEncodingFilter");
//        registration.setOrder(1);
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

//    @Bean
//    public FilterRegistrationBean jwtFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setName("JWTFilter");
//        registration.setFilter(new JWTFilter());
//        registration.addUrlPatterns("/controller/token/*");
//        registration.setOrder(4);
//        return registration;
//    }

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

}
