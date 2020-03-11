package com.scloud.order.service.impl;

import com.scloud.order.dto.OrderDto;
import com.scloud.order.entity.OrderMaster;
import com.scloud.order.enums.OrderStatusEnum;
import com.scloud.order.enums.PayStatusEnum;
import com.scloud.order.repository.OrderDetailRepository;
import com.scloud.order.repository.OrderMasterRepository;
import com.scloud.order.service.OrderService;
import com.scloud.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDto create(OrderDto orderDto) {
        //Todo 查询商品信息（调用商品服务）
        //Todo 计算总价
        //Todo 口库存（调用商品服务）
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(KeyUtil.getUniqueKey());
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDto;
    }
}
