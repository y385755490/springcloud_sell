package com.ymd.product.service.impl;

import com.ymd.product.dataObject.ProductCategory;
import com.ymd.product.repository.ProductCategoryRepository;
import com.ymd.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(CategoryTypeList);
    }
}
