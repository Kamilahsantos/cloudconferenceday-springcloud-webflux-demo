package com.cloudconferenceday.webflux;

import com.cloudconferenceday.webflux.model.Order;

public class OrderMock {

    public static Order order() {
        return Order.builder()
                .uuid("de37b55d-fad2-4248-946e-04e2963e6580")
                .sellerId("893b04b8-2881-4ff1-b295-d480644117f4")
                .customerId("03946219-0e87-4ee7-89bc-f0973931921d")
                .build();
    }
}