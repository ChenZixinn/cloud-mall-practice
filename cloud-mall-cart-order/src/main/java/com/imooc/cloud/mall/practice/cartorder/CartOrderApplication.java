package com.imooc.cloud.mall.practice.cartorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@EnableFeignClients
@MapperScan(value = "com.imooc.cloud.mall.practice.cartorder.model.dao")
public class CartOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartOrderApplication.class, args);
    }
}
