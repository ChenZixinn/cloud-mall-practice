package com.imooc.cloud.mall.practice.cartorder.model.dao;

import com.imooc.cloud.mall.practice.cartorder.model.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectOrderItemListByOrderNo(@Param("orderNo") String orderNo);
}