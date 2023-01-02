package com.imooc.cloud.mall.practice.categoryproduct.model.dao;

import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    List<Product> selectListForAdmin();

    List<Product> selectListForCustomer(@Param("query") ProductListQuery query);
}