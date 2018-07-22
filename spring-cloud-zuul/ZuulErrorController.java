package com.x.spring.cloud.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ZuulErrorController {

    @RequestMapping("/error")
    public Map<String, Object> error() {
        RequestContext context = RequestContext.getCurrentContext();
        context.getResponse().setContentType("application/json; charset=utf8");
        Throwable throwable = context.getThrowable();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", 500);
        result.put("exception", getOriginException(throwable));
        return result;
    }

    private Throwable getOriginException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }
}
