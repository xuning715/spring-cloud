package com.x.application.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.x.application.model.ViewParam;
import com.x.application.model.ViewResult;
import com.x.security.rpc.SecurityRpcService;
import com.x.security.model.Application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @Value("${server.port}")
//    private String port;

    @Reference
    private SecurityRpcService securityRpcService;

//    @Reference(interfaceName = "com.x.bpm.rpc.BpmRpcService")
//    private BpmRpcService bpmRpcService;

    @RequestMapping(value = "/test")
    @HystrixCommand(fallbackMethod = "fallbackConsumer")
    public ViewResult test(ViewParam viewParam) throws Exception {
        System.out.println("=============json=====================" + JSON.toJSONString(viewParam));

        if (viewParam.getFlag() == 2) {
            System.out.println("==================================" + 2);
            throw new RuntimeException("fallback");
        } else if (viewParam.getFlag() == 3) {
            System.out.println("==================================" + 3);
            Thread.sleep(6000);
        } else {
            System.out.println("==================================" + 1);
        }

//        AsyncResult<List<Application>> asyncResult = new AsyncResult<List<Application>>() {
//            @Override
//            public List<Application> invoke() {
//        StateMap stateMap = new StateMap();
//        stateMap.putState("intentionState", 20);
//        Rule rule = new Rule();
//        rule.setApplicationId("171114085301365");
//        rule.setProcessId(1);
//        List<Operation>  operationList = bpmRpcService.selectOperationList(stateMap, rule);
//        for (Operation operation : operationList) {
//            System.out.println(operation.getOperationName());
//        }

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

	@RequestMapping(value = "/first")
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());  
        map.put("request Url", request.getRequestURL());  
        return map;  
    }  
  
    @RequestMapping(value = "/sessions")
    public Object sessions (HttpServletRequest request){  
        Map<String, Object> map = new HashMap<>();  
        map.put("sessionId", request.getSession().getId());  
        map.put("message", request.getSession().getAttribute("request Url"));
        return map;  
    }  

}
