package com.x.spring.cloud.zuul.filter;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

//@Component
public class MyErrorAttribute {//} extends DefaultErrorAttributes {
//    @Override
//    public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, boolean includeStackTrace) {
//        Map<String, Object> result = super.getErrorAttributes(requestAttributes, includeStackTrace);
//        result.put("status", 222);
//        result.put("error", "error");
//        result.put("exception", "exception");
//        result.put("message", "message");
//        return result;
//    }
}
