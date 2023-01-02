package com.imooc.cloud.mall.practice.cartorder.feign;

import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.UpdateProductReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 商品的FeignClient
 */
@FeignClient(value = "cloud-mall-category-product")
public interface ProductFeignClient {

    @GetMapping("/product/detailForFeign")
    Product detailForFeign(@RequestParam Integer id);

    @PostMapping("/product/updateStockForFeign")
    void updateStockForFeign(@RequestParam Integer productId, @RequestParam Integer stock);
}
