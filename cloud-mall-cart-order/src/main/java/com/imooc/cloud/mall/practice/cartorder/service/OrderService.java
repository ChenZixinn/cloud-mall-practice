package com.imooc.cloud.mall.practice.cartorder.service;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.cartorder.model.pojo.Order;
import com.imooc.cloud.mall.practice.cartorder.model.request.CreateOrderReq;
import com.imooc.cloud.mall.practice.cartorder.model.vo.OrderVO;

public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    OrderVO getOrderVO(Order order);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    void finish(String orderNo);

    void orderDelivered(String orderNo);

    String qrcode(String orderNo);

    void pay(String orderNo);
}
