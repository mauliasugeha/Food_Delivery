package com.example.foodDelivery.service;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.producer.OrderProducer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    public static Map<String, OrderEvent> orderStore = new HashMap<>();

    private final OrderProducer orderProducer;

    public OrderService(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public OrderEvent createOrder(OrderEvent order) {

        order.setStatus("CREATED");

        orderStore.put(order.getOrderId(), order);
        orderProducer.sendOrder(order);

        System.out.println("=================================");
        System.out.println("🧾 ORDER DIBUAT");
        System.out.println("ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Status: MENUNGGU PEMBAYARAN");
        System.out.println("=================================");

        return order;
    }

    public OrderEvent getOrder(String id) {
        return orderStore.get(id);
    }
}
