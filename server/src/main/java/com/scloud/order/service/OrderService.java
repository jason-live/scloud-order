package com.scloud.order.service;

import com.scloud.order.dto.OrderDto;

public interface OrderService {
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 完成订单
     * @param orderId
     * @return
     */
    OrderDto finish(String orderId);
}
