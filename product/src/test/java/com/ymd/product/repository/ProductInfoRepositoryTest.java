package com.ymd.product.repository;

import com.ymd.product.dataObject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Test
    void findByProductStatus() {
        List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(0);
        Assert.assertTrue(byProductStatus.size()>0);
    }

    @Test
    public void findByProductIdIn() throws Exception{
        List<ProductInfo> list = productInfoRepository.findByProductIdIn(
                Arrays.asList("157875196366160022", "157875227953464068"));
        Assert.assertTrue(list.size() == 2);
    }
}