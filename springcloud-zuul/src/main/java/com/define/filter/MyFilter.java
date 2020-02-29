package com.define.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    //过滤器类型，这里定义为pre，代表会在请求被路由之前执行
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器的执行顺序，当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
    @Override
    public int filterOrder() {
        return 0;
    }

    //判断该过滤器是否需要被执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤器的具体逻辑
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty" + "\n");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}
