package com.scloud.order.controller;

import com.scloud.order.config.GrilConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GirlController {
    @Autowired
    private GrilConfig grilConfig;

    public String getGirl(){
        return grilConfig.getName() + grilConfig.getAge();
    }
}
