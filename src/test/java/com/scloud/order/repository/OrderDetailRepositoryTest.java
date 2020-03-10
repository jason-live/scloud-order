package com.scloud.order.repository;

import com.scloud.order.OrderApplicationTests;
import com.scloud.order.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderDetailRepositoryTest extends OrderApplicationTests {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testSave() {
        OrderDetail orderDetail = OrderDetail.builder().detailId("adfasdfas")
                .orderId("sadfasfd")
                .productIcon("http://xxxx.com")
                .productId("157875227953464068")
                .productName("慕斯蛋糕")
                .productPrice(new BigDecimal(4.8))
                .productQuantity(3)
                .build();
        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertTrue(result != null);
    }
}