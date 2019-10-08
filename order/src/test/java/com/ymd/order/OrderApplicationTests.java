package com.ymd.order;

import com.ymd.client.ProductClient;
import com.ymd.common.ProductInfoOutput;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {
    @Autowired
    private ProductClient productClient;

    @Test
    void contextLoads() {
        List<ProductInfoOutput> productInfoOutputs = productClient.listForOrder(Arrays.asList("1234567"));
        System.out.println(productInfoOutputs.toString());
    }

}
