package com.x.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ErrorFilter extends ZuulFilter {
    private static final String ERROR_STATUS_CODE_KEY = "error.status_code";
    public static final String DEFAULT_ERR_MSG = "系统繁忙,请稍后再试";
    protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
//        return ctx.containsKey(ERROR_STATUS_CODE_KEY);
//        return true;
        return ctx.containsKey("error.status_code") && !ctx.getBoolean(SEND_ERROR_FILTER_RAN, false);
    }

    @Override
    public Object run() {
        System.out.println("2222222222222222222222222222222222222");
        RequestContext ctx = RequestContext.getCurrentContext();
//        String message = (String) context.get("error.message");
//        Throwable throwable = context.getThrowable();
//        throwable = this.getOriginException(throwable);

//        throw new RuntimeException();
//        RequestContext ctx = RequestContext.getCurrentContext();
//
//        try {
        HttpServletRequest request = ctx.getRequest();
//
        int statusCode = (Integer) ctx.get(ERROR_STATUS_CODE_KEY);
        String message = (String) ctx.get("error.message");
        System.out.println("statusCode===========" + statusCode);
        System.out.println("message===========" + message);
        if (ctx.containsKey("error.exception")) {
            Throwable e = (Exception) ctx.get("error.exception");
            Throwable re = getOriginException(e);
            if (re instanceof java.net.ConnectException) {
                message = "Real Service Connection refused";
                System.out.println("uri:{},error:{}" + request.getRequestURI() + re.getMessage());
            } else if (re instanceof java.net.SocketTimeoutException) {
                message = "Real Service Timeout";
                System.out.println("uri:{},error:{}" + request.getRequestURI() + re.getMessage());
            } else if (re instanceof com.netflix.client.ClientException) {
                message = re.getMessage();
                System.out.println("uri:{},error:{}" + request.getRequestURI() + re.getMessage());
            } else {
                System.out.println("Error during filtering" + e);
            }
        }

        if (StringUtils.isBlank(message)) {
            request.setAttribute("javax.servlet.error.status_code", statusCode);
            request.setAttribute("javax.servlet.error.message", message);
            message = DEFAULT_ERR_MSG;
            ErrorBean errorBean = new ErrorBean();
            errorBean.setMessage(message);
            errorBean.setReason("message");
            return errorBean;
        } else {
            return null;
        }


//        Throwable throwable = ctx.getThrowable();
//        System.out.println("this is a ErrorFilter : {}" + throwable.getCause().getMessage());
//        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        ctx.set("error.exception", throwable.getCause());
    }

    private Throwable getOriginException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }

    private static class ErrorBean {
        private String message;

        private String reason;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

}
