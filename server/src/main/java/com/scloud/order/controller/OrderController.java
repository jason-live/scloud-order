package com.scloud.order.controller;

import com.scloud.order.converter.OrderForm2OrderDtoConverter;
import com.scloud.order.dto.OrderDto;
import com.scloud.order.enums.ResultEnum;
import com.scloud.order.exception.OrderException;
import com.scloud.order.form.OrderForm;
import com.scloud.order.service.OrderService;
import com.scloud.order.utils.ResultVoUtil;
import com.scloud.order.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 1. 参数校验
     * 2. 查询商品信息（调用商品服务）
     * 3. 计算总价
     * 4. 口库存（调用商品服务）
     * 5. 订单入库
     */
    @PostMapping("create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAMS_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm => orderDto
        log.info("【创建订单参数】{}", orderForm);
        OrderDto orderDto = OrderForm2OrderDtoConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        OrderDto result = orderService.create(orderDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVoUtil.success(map);
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVo<OrderDto> finish(@RequestParam("orderId") String orderId) {
        return ResultVoUtil.success(orderService.finish(orderId));
    }
}
