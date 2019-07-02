package com.ymd.product.controller;

import com.ymd.product.dataObject.ProductCategory;
import com.ymd.product.dataObject.ProductInfo;
import com.ymd.product.dto.CartDTO;
import com.ymd.product.enums.ResultEnum;
import com.ymd.product.service.CategoryService;
import com.ymd.product.service.ProductService;
import com.ymd.product.utils.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.ProductInfoVO;
import vo.ProductVO;
import vo.ResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    /**
     * 1.查询所有在架的商品
     * 2.获取类目type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list(){
        //1.查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        //2.获取类目type列表
        List<Integer> collect = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
        //3.查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(collect);
        //4.构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表（给订单服务用的）
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return this.productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }

}
