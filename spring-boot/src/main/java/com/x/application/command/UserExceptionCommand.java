package com.x.application.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.stereotype.Component;

//@Component
public class UserExceptionCommand {//} extends HystrixCommand<String> {

//    public UserExceptionCommand() {
//        super(HystrixCommandGroupKey.Factory.asKey("userGroup"));
//    }
//
//    @Override
//    protected String run() throws Exception {
//        return "UserExceptionCommand";
//    }
//
//    @Override
//    protected String getFallback() {
//        return "UserExceptionCommand服务降级，暂时不可用";
//    }
}