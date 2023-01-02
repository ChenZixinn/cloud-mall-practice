package com.imooc.cloud.mall.practice.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.ProductListReq;


public interface ProductService {

    void addProduct(AddProductReq addProductReq);

    void updateProduct(Product updateProductReq);

    void deleteProduct(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listProductForAdmin(Integer pageSize, Integer pageNum);

    PageInfo ListProductForCustomer(ProductListReq productListReq);

    Product detail(Integer id);

    void updateStockForFeign(Integer productId, Integer stock);
}
