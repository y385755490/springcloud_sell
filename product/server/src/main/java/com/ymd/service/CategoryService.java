package com.ymd.service;

import com.ymd.dataObject.ProductCategory;

import java.util.List;

public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);
}
