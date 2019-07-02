package com.ymd.product.exception;

import com.ymd.product.enums.GoodsEnum;

public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ProductException(GoodsEnum goodsEnum) {
        super(goodsEnum.getMessage());
        this.code = goodsEnum.getCode();
    }
}
