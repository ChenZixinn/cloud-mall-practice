package com.imooc.cloud.mall.practice.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;


import java.util.List;

/**
 * 目录分类service接口
 */

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategoryReq);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
