package com.ymd.order.controller;

import com.ymd.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/girl")
public class GirlController {
    @Autowired
    private GirlConfig girlConfig;

    @RequestMapping("/print")
    public String print() {
        return "name: " + girlConfig.getName() + ",age: " + girlConfig.getAge();
    }

}
