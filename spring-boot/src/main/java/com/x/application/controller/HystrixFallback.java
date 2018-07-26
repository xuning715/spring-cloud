package com.x.application.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class HystrixFallback {
    public Logger logger = LogManager.getLogger(getClass());
//    public Logger logger = LoggerFactory.getLogger(getClass());

    private static ViewResult viewResult = new ViewResult(0, "服务异常", null);

    public ViewResult fallbackConsumer(ViewParam viewParam) {
        System.out.println("==================================fallback==" + (viewParam.getFlag()));
        logger.error(viewResult.getMessage());
        logger.info(viewResult.getMessage());
//        throw new BusinessException(viewResult.getMessage());
        return viewResult;
    }
}
