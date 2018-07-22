package com.x.application.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.x.application.config.ApplicationConfig;
import com.x.security.model.Application;
import com.x.security.rpc.SecurityRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@DefaultProperties(
//        groupKey = "consumer-groupKey",
//        commandProperties = {
//                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//        },
//        threadPoolProperties = {
//                @HystrixProperty(name = "coreSize", value = "2")
//        }
//)
public class TestController extends HystrixFallback {

    @RequestMapping(value = "/xxx")
    @HystrixCommand(fallbackMethod = "fallbackConsumer")
    public ViewResult getApplicationList(ViewParam viewParam) {
        ViewResult viewResult = new ViewResult();
        viewResult.setCode(1);
        return viewResult;
    }

}
