package com.x.security.register;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class GlobalRegister {

    public GlobalRegister() {

    }

}
