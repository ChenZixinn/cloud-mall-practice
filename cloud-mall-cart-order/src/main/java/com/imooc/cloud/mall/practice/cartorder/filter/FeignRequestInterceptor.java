package com.imooc.cloud.mall.practice.cartorder.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * feign请求拦截器
 *      feign调用不经过网关
 *      需要携带网关的session信息
 */
@EnableFeignClients
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null){
            while (headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()){
                    String value = values.nextElement();
                    requestTemplate.header(name, value);
                }
            }
        }
    }
}
