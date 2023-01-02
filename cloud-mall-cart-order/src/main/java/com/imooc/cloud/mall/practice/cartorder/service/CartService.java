package com.imooc.cloud.mall.practice.cartorder.service;


import com.imooc.cloud.mall.practice.cartorder.model.vo.CartVO;

import java.util.List;

public interface CartService {

    List<CartVO> selectByUserId(Integer userId);
    List<CartVO> addCart(Integer userId, Integer productId, Integer count);

    List<CartVO> updateCart(Integer userId, Integer productId, Integer count);

    List<CartVO> deleteCart(Integer userId, Integer productId);

    List<CartVO> selectCart(Integer userId, Integer productId, Integer selected);

    List<CartVO> selectAllCart(Integer userId, Integer selected);
}
