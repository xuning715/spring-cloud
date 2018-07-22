package com.x.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class ErrorExtFilter extends SendErrorFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 15;    // 大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("1111111111111111111111111111111111111111");
        // 判断：仅处理来自post过滤器引起的异常
//        RequestContext ctx = RequestContext.getCurrentContext();
//        ZuulFilter failedFilter = (ZuulFilter) ctx.get("error.status_code");
//        if(failedFilter != null) {
//            if(failedFilter != null && failedFilter.filterType().equals("post")) {
//            return true;
//        }
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            Throwable exception = context.getThrowable();
//            ZuulException exception = this.findZuulException(context.getThrowable());
            System.out.println("进入系统异常拦截" + exception);

            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json; charset=utf8");
//            response.setStatus(exception.nStatusCode);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("{code:" + exception.nStatusCode + ",message:\"" + exception.getMessage() + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }

        } catch (Exception var5) {
            ReflectionUtils.rethrowRuntimeException(var5);
        }

        return null;
    }

    ZuulException findZuulException(Throwable throwable) {
        if (ZuulRuntimeException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause().getCause();
        } else if (ZuulException.class.isInstance(throwable.getCause())) {
            return (ZuulException) throwable.getCause();
        } else {
            return ZuulException.class.isInstance(throwable) ? (ZuulException) throwable : new ZuulException(throwable, 500, (String) null);
        }
    }

}
