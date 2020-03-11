package com.scloud.order.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAMS_ERROR(1, "参数不正确"),
    CAET_EMPTY(2, "购物车信息不能为空")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
