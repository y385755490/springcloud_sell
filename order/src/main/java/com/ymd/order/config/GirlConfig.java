package com.ymd.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@ConfigurationProperties("girl")
@RefreshScope
public class GirlConfig {
    private String name;
    private Integer age;
}
