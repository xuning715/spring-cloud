package com.x.application.controller;

public class HystrixFallback {
    private static ViewResult viewResult = new ViewResult(0, "服务异常", null);

    public ViewResult fallbackConsumer(ViewParam viewParam) {
//        System.out.println("==================================fallback==" + JSONUtils.toJSONString(viewParam));
//        throw new RuntimeException("error");
        return viewResult;
    }
}
