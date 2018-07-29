package com.x.application.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class HystrixFallback {
    public Logger logger = LogManager.getLogger(getClass());
//    public Logger logger = LoggerFactory.getLogger(getClass());

    private static ViewResult viewResult = new ViewResult(0, "服务异常", null);

    public ViewResult fallbackConsumer(ViewParam viewParam, Throwable throwable) {
        System.out.println("==================================fallback==" + (viewParam.getFlag()));
//        throwable = this.getOriginException(throwable);
        logger.error(throwable.getMessage());
//        logger.info(viewResult.getMessage());
//        throw new BusinessException(viewResult.getMessage());
        return viewResult;
    }

    private Throwable getOriginException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }
}
