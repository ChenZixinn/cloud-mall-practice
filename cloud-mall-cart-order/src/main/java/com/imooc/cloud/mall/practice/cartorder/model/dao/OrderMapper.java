package com.imooc.cloud.mall.practice.cartorder.model.dao;

import com.imooc.cloud.mall.practice.cartorder.model.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    List<Order> selectByUserId(@Param("userId") Integer userId);

    List<Order> selectList();
}