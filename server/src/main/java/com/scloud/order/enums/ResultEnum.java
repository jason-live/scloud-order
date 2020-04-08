package com.scloud.order.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    PARAMS_ERROR(1, "参数不正确"),
    CART_EMPTY(2, "购物车信息不能为空"),
    ORDER_NOT_EXIST(3, "订单不存在"),
    ORDER_STATUS_ERROR(4, "订单状态错误"),
    ORDER_DETAIL_NOT_EXIST(5, "订单详情不存在")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
