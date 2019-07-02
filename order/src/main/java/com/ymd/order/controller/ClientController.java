package com.ymd.order.controller;

import com.ymd.order.client.ProductClient;
import com.ymd.order.dataobject.ProductInfo;
import com.ymd.order.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getProductMsg")
    public String getProductMsg(){
        //第一种方式(直接使用restTemplate，url写死)
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
        //第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate）
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");

//        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
//        String response = restTemplate.getForObject(url, String.class);
        //第三种方式(利用@LoadBalanced，可在restTemplate里使用应用名字)
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        String response = productClient.productMsg();
        System.out.println("response=" + response);

        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productInfos = this.productClient.listForOrder(
                Arrays.asList("157875196366160022", "157875227953464068"));
        System.out.println("response=" + productInfos);
        return "ok";
    }

    @GetMapping("/product/decreaseStock")
    public String decreaseStock(){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("157875196366160022");
        cartDTO.setProducQuantity(2);
        this.productClient.decreaseStock(Arrays.asList(cartDTO));
        return "ok";
    }
}
