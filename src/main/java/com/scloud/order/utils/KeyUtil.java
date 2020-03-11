package com.scloud.order.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer integer = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(integer);
    }
}
