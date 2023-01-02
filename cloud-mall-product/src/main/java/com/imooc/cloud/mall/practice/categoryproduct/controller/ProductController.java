package com.imooc.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.model.dao.ProductMapper;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.ProductListReq;
import com.imooc.cloud.mall.practice.categoryproduct.service.ProductService;
import com.imooc.cloud.mall.practice.commom.commom.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台商品controller
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @GetMapping("/product/detailForFeign")
    public Product detailForFeign(@RequestParam Integer id){
        Product product = productService.detail(id);
        return product;
    }


    @ApiOperation("用户商品列表")
    @GetMapping("/product/list")
    public ApiRestResponse listProductForCustomer(ProductListReq productListReq){
        PageInfo pageInfo = productService.ListProductForCustomer(productListReq);
        return ApiRestResponse.success(pageInfo);
    }
}
