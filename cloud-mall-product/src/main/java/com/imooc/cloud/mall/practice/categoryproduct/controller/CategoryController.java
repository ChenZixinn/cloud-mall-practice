package com.imooc.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.UpdateCategoryReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;
import com.imooc.cloud.mall.practice.categoryproduct.service.CategoryService;
import com.imooc.cloud.mall.practice.commom.commom.ApiRestResponse;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 目录controller
 */
@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 添加目录
     * @param session
     * @param addCategoryReq 传入的参数
     * @return
     * @throws ImoocMallException
     */
    @ApiOperation("后台添加商品分类")
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryReq addCategoryReq) throws ImoocMallException {
        // 判断字段是否为空
//        if (addCategoryReq.getName() == null || addCategoryReq.getOrderNum() == null ||
//                addCategoryReq.getParentId() == null || addCategoryReq.getType() == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
//        }
        // 添加分类
        categoryService.add(addCategoryReq);
        return  ApiRestResponse.success();
    }

    /**
     * 更新商品分类信息
     * @param session
     * @param updateCategoryReq 商品分类数据，id不能为空
     * @return
     */
    @ResponseBody
    @ApiOperation("更新商品分类信息")
    @PostMapping("/admin/category/update")
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq, category);
        categoryService.update(category);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @ApiOperation("删除分类信息")
    @PostMapping("/admin/category/delete")
    public ApiRestResponse deleteCategory(@RequestParam("id") Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ResponseBody
    @ApiOperation("后台目录列表")
    @GetMapping("/admin/category/list")
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ResponseBody
    @ApiOperation("前台目录列表")
    @GetMapping("/category/list")
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOList);
    }
}
