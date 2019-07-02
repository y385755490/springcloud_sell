package com.ymd.product.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0,"成功"),
    FAIL(1,"失败");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
