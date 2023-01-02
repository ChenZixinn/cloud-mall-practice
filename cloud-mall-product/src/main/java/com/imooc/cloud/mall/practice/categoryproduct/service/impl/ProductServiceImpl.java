
package com.imooc.cloud.mall.practice.categoryproduct.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.model.dao.ProductMapper;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.ProductListReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.imooc.cloud.mall.practice.categoryproduct.model.query.ProductListQuery;
import com.imooc.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.imooc.cloud.mall.practice.categoryproduct.service.ProductService;
import com.imooc.cloud.mall.practice.commom.commom.Constant;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallExceptionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Override
    public void addProduct(AddProductReq addProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(product.getName());
        if(productOld != null){
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void updateProduct(Product updateProductReq){
        Product oldProduct = productMapper.selectByName(updateProductReq.getName());
        if (oldProduct != null && oldProduct.getId().equals(updateProductReq.getId())){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProductReq);
        if (count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        // 查找
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null)
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_FOUNT);
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0)
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus){
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo listProductForAdmin(Integer pageSize, Integer pageNum){
        PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<Product> productList = productMapper.selectListForAdmin();
        // 默认按时间排序
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    @Override
    public PageInfo ListProductForCustomer(ProductListReq productListReq){
        ProductListQuery query = new ProductListQuery();

        // 关键词赋值
        if (!StringUtils.isEmpty(productListReq.getKeyword())){
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            query.setKeyword(keyword);
        }
        // 目录赋值：拿到目录id和parent为该id的id
        if (productListReq.getCategoryId() != null){
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVOList, categoryIds);
            query.setCategoryIds(categoryIds);
        }

        // 排序处理
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        }else{
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }

        List<Product> productList = productMapper.selectListForCustomer(query);

        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds){
        for (CategoryVO categoryVO : categoryVOList) {
            if (categoryVO != null){
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(), categoryIds);
            }
        }
    }
    @Override
    public Product detail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateStockForFeign(Integer productId, Integer stock) {
        Product product = new Product();
        product.setId(productId);
        product.setStock(stock);
        productMapper.updateByPrimaryKeySelective(product);
    }


}
