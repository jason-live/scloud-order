package com.scloud.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scloud.order.dto.OrderDto;
import com.scloud.order.entity.OrderDetail;
import com.scloud.order.enums.ResultEnum;
import com.scloud.order.exception.OrderException;
import com.scloud.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDtoConverter {
    public static OrderDto convert(OrderForm orderForm) {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.error("【json转换】错误，string={}", orderForm.getItems());
            throw new OrderException(ResultEnum.PARAMS_ERROR);
        }

        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }
}
