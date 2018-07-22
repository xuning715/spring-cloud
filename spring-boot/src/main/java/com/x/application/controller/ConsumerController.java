package com.x.application.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.x.application.config.ApplicationConfig;
import com.x.security.rpc.SecurityRpcService;
import com.x.security.model.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class ConsumerController extends HystrixFallback {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Value("${server.port}")
    private String port;

    @Reference(interfaceName = "com.x.security.rpc.SecurityRpcService")
    private SecurityRpcService securityRpcService;

    @RequestMapping(value = "/test")
    @HystrixCommand(fallbackMethod = "fallbackConsumer")
    public ViewResult test(ViewParam viewParam) throws Exception {
        if (viewParam.getFlag() == 2) {
            System.out.println("==================================" + 2);
            throw new RuntimeException("fallback");
        } else if (viewParam.getFlag() == 3) {
            System.out.println("==================================" + 3);
            Thread.sleep(5000);
        } else {
            System.out.println("==================================" + 1);
        }

//        AsyncResult<List<Application>> asyncResult = new AsyncResult<List<Application>>() {
//            @Override
//            public List<Application> invoke() {
        Application application = new Application();
        List<Application> list = securityRpcService.selectApplicationList(application);
        ViewResult viewResult = new ViewResult();
        viewResult.setCode(1);
        viewResult.setResult(list);
        return viewResult;
//            }
//        };
//        return asyncResult.get();
    }

}
