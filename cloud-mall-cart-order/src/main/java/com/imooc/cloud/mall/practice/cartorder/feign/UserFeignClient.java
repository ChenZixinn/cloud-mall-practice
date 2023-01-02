package com.imooc.cloud.mall.practice.cartorder.feign;

import com.imooc.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {
    @GetMapping("/getUser")
    User getUser();
}
