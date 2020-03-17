package com.scloud.order.controller;

import com.scloud.order.client.ProductClient;
import com.scloud.order.dto.CartDto;
import com.scloud.order.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class ClientController {
    @Autowired
    ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {

        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("157875196366160022", "164103465734242707"));
        log.info("response={}", productInfoList);
        return "ok";
    }

    @GetMapping("/productDecreaseStock")
    public void decreaseStock() {
        productClient.decreaseStock(Arrays.asList(new CartDto("157875196366160022", 3)));
    }
}
