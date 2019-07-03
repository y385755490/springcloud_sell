package com.ymd.common;

import lombok.Data;

@Data
public class DecreaseStockInput {
    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer producQuantity;
}
