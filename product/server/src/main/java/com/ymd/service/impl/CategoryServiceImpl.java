package com.ymd.service.impl;

import com.ymd.dataObject.ProductCategory;
import com.ymd.repository.ProductCategoryRepository;
import com.ymd.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
