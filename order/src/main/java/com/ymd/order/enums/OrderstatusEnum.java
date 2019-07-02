package com.ymd.order.enums;

import lombok.Getter;

@Getter
public enum OrderstatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消");

    private Integer code;

    private String message;

    OrderstatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
