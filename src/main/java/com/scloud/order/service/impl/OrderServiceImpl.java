package com.scloud.order.service.impl;

import com.scloud.order.client.ProductClient;
import com.scloud.order.dto.CartDto;
import com.scloud.order.dto.OrderDto;
import com.scloud.order.entity.OrderDetail;
import com.scloud.order.entity.OrderMaster;
import com.scloud.order.entity.ProductInfo;
import com.scloud.order.enums.OrderStatusEnum;
import com.scloud.order.enums.PayStatusEnum;
import com.scloud.order.repository.OrderDetailRepository;
import com.scloud.order.repository.OrderMasterRepository;
import com.scloud.order.service.OrderService;
import com.scloud.order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtil.getUniqueKey();
        //Todo 查询商品信息（调用商品服务
        List<String> productIdList = orderDto.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        log.info("productIdList={}", productIdList);
        List<ProductInfo> productInfoList = productClient.listForOrder(productIdList);
        //Todo 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail: orderDto.getOrderDetailList()){
            for (ProductInfo productInfo: productInfoList) {
                if (orderDetail.getProductId().equals(productInfo.getProductId())) {
                    // 单价*数量
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    // 订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //Todo 口库存（调用商品服务）
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDtoList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDto;
    }
}
