package com.ymd.product.service.impl;

import com.ymd.product.ProductApplicationTests;
import com.ymd.product.dataObject.ProductInfo;
import com.ymd.product.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
class ProductServiceImplTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertTrue(upAll.size() > 0);
    }

    @Test
    public void findList() throws Exception{
        List<ProductInfo> list = productService.findList(Arrays.asList("157875196366160022", "157875227953464068"));
        Assert.assertTrue(list.size() == 2);
    }
}