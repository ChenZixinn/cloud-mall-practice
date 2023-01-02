package com.imooc.cloud.mall.practice.zuul.filter;

import com.imooc.cloud.mall.practice.commom.commom.Constant;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallExceptionEnum;
import com.imooc.cloud.mall.practice.user.model.pojo.User;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户鉴权过滤器
 */
@Component
public class UserFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 判断请求的链接决定是否使用过滤器
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        System.out.println("======"+requestURI);
        if (requestURI.contains("images") || requestURI.contains("pay")) {
            return false;
        }
        if (requestURI.contains("cart") || requestURI.contains("order") || requestURI.contains("getUser")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        // 通过session判断
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (user == null){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\n" +
                    "    \"status\": "+ ImoocMallExceptionEnum.NEED_LOGIN.getCode() +",\n" +
                    "    \"msg\": \"NEED LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            currentContext.setResponseStatusCode(200);
        }
        return null;
    }
}
