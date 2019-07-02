package com.ymd.product.service.impl;

import com.ymd.product.ProductApplicationTests;
import com.ymd.product.dataObject.ProductCategory;
import com.ymd.product.repository.ProductCategoryRepository;
import com.ymd.product.service.CategoryService;
import com.ymd.product.service.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
class CategoryServiceImplTest extends ProductApplicationTests {
    @Autowired
    private CategoryService categoryService;

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(11, 22));
        Assert.assertTrue(byCategoryTypeIn.size() > 0);
    }
}