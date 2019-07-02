package com.ymd.product.enums;

import lombok.Getter;

@Getter
public enum GoodsEnum {
    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_ERROR(2, "库存有误"),
    ;

    private Integer code;

    private String message;

    GoodsEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
