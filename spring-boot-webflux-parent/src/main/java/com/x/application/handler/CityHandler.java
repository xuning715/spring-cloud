package com.x.application.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.x.security.model.Application;
import com.x.security.rpc.SecurityRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CityHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference
    private SecurityRpcService securityRpcService;

    @HystrixCommand(fallbackMethod = "fallbackHelloCity")
    public Mono<ServerResponse> helloCity(ServerRequest request) {
        Application application = new Application();
        List<Application> list = securityRpcService.selectApplicationList(application);
        Integer.parseInt("x");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(list));
    }


    public Mono<ServerResponse> fallbackHelloCity(ServerRequest request, Throwable throwable) {
        logger.error(JSON.toJSONString(request), throwable);
        return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject("{0, \"服务异常\", null}"));
    }

}
