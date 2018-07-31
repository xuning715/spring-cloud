package com.x.application.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.x.application.model.ViewParam;
import com.x.application.model.ViewResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
