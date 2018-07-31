package com.x.application.controller;

import com.alibaba.fastjson.JSON;
import com.x.application.model.ViewParam;
import com.x.application.model.ViewResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HystrixFallback {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static ViewResult viewResult = new ViewResult(0, "服务异常", null);

    public ViewResult fallbackConsumer(ViewParam viewParam, Throwable throwable) {
        logger.error(JSON.toJSONString(viewParam), throwable);
        return viewResult;
    }

}
