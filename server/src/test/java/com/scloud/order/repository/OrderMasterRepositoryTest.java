package com.scloud.order.repository;

import com.scloud.order.OrderApplicationTests;
import com.scloud.order.entity.OrderMaster;
import com.scloud.order.enums.OrderStatusEnum;
import com.scloud.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster orderMaster = OrderMaster.builder().orderId("asd-asdf-asdfasfd")
        .buyerAddress("上海市")
        .buyerName("jason")
        .buyerOpenid("wq342341234124")
        .buyerPhone("15161461631")
        .orderAmount(new BigDecimal(3.2))
        .orderStatus(OrderStatusEnum.NEW.getCode())
        .payStatus(PayStatusEnum.WAIT.getCode())
        .build();
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertTrue(result != null);
    }
}