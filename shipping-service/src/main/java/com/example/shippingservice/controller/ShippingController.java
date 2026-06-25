package com.example.shippingservice.controller;

import com.example.shippingservice.consumer.ShippingConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
    @GetMapping("/status/{orderId}")
    public String getShippingStatus(@PathVariable String orderId) {
        return ShippingConsumer.getShippingStatus(orderId);
    }
}
