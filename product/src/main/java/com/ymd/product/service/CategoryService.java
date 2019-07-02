package com.ymd.product.service;

import com.ymd.product.dataObject.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);
}
