package com.imooc.cloud.mall.practice.categoryproduct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringCloudApplication
@EnableFeignClients
@EnableRedisHttpSession
@MapperScan(value = "com.imooc.cloud.mall.practice.categoryproduct.model.dao")
public class CategoryProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryProductApplication.class, args);
    }
}
