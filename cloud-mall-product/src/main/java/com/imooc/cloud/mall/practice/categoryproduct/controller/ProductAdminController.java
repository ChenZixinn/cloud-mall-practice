package com.imooc.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.cloud.mall.practice.categoryproduct.common.ProductConstant;
import com.imooc.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.model.request.UpdateProductReq;
import com.imooc.cloud.mall.practice.categoryproduct.service.ProductService;
import com.imooc.cloud.mall.practice.commom.commom.ApiRestResponse;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallExceptionEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 后台商品管理controller
 */
@RestController
public class ProductAdminController {
    @Value("${file.upload.ip}")
    String ip;
    @Value("${file.upload.port}")
    Integer port;
    @Autowired
    ProductService productService;

    @ApiOperation("后台添加商品信息接口")
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.addProduct(addProductReq);
        return ApiRestResponse.success();
    }


    @ApiOperation("后台上传图片接口")
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        // 生成文件名称
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;

        File fileDir = new File(ProductConstant.FILE_UPLOAD_DIR);
        File destFile = new File(ProductConstant.FILE_UPLOAD_DIR + newFileName);

        if (!fileDir.exists()) {
            if (!fileDir.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }

        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/category-product/images/" + newFileName);
        } catch (URISyntaxException e) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), this.ip, this.port, null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @ApiOperation("后台更新商品信息接口")
    @PostMapping("admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.updateProduct(product);
        return ApiRestResponse.success();
    }

    @PostMapping("/product/updateStockForFeign")
    public void updateStockForFeign(@RequestParam Integer productId, @RequestParam Integer stock){
        productService.updateStockForFeign(productId, stock);
    };

    @ApiOperation("后台批量上下架接口")
    @PostMapping("admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam("ids") Integer[] ids,@RequestParam("sellStatus") Integer sellStatus){
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表")
    @GetMapping("admin/product/list")
    public ApiRestResponse listProduct(@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum){
        PageInfo pageInfo = productService.listProductForAdmin(pageSize, pageNum);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("后台删除商品")
    @PostMapping("admin/product/delete")
    public ApiRestResponse listProduct(@RequestParam("id") Integer id){
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }
}