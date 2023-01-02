package com.imooc.cloud.mall.practice.cartorder.controller;

import com.imooc.cloud.mall.practice.cartorder.feign.UserFeignClient;
import com.imooc.cloud.mall.practice.cartorder.model.pojo.Cart;
import com.imooc.cloud.mall.practice.cartorder.model.vo.CartVO;
import com.imooc.cloud.mall.practice.cartorder.service.CartService;
import com.imooc.cloud.mall.practice.commom.commom.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserFeignClient userFeignClient;

    @ApiOperation("添加商品到购物车接口")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        Cart cart = new Cart();
        List<CartVO> cartVOList = cartService.addCart(userFeignClient.getUser().getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("购物车列表接口")
    @GetMapping("/list")
    public ApiRestResponse list() {
        // 查询该用户的购物车列表
        List<CartVO> cartVOs = cartService.selectByUserId(userFeignClient.getUser().getId());

        return ApiRestResponse.success(cartVOs);
    }

    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {
        List<CartVO> cartVOList = cartService.updateCart(userFeignClient.getUser().getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("删除购物车商品")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        List<CartVO> cartVOList = cartService.deleteCart(userFeignClient.getUser().getId(), productId);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("选择/取消选择购物车商品")
    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected) {
        List<CartVO> cartVOList = cartService.selectCart(userFeignClient.getUser().getId(), productId, selected);
        return ApiRestResponse.success(cartVOList);
    }

    @ApiOperation("全选/取消全选购物车商品")
    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(@RequestParam Integer selected) {
        List<CartVO> cartVOList = cartService.selectAllCart(userFeignClient.getUser().getId(), selected);
        return ApiRestResponse.success(cartVOList);
    }
}
