package com.example.shippingservice.consumer;

import com.example.shippingservice.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingConsumer {
    private static final Map<String, String> shippingStatus = new HashMap<>();

    @KafkaListener(topics = "payment-success-topic", groupId = "shipping-group")
    public void processShipping(OrderEvent order) {
        order.setStatus("SHIPPED");
        shippingStatus.put(order.getOrderId(), "SHIPPED");

        System.out.println("=========== NOTA DIGITAL ==========");
        System.out.println("Customer : " + order.getCustomerName());
        System.out.println("Produk   : " + order.getProductName());
        System.out.println("Qty      : " + order.getQuantity());
        System.out.println("Total    : Rp " + order.getTotalPrice());
        System.out.println("Status   : " + order.getStatus());
        System.out.println("===================================");
    }

    public static String getShippingStatus(String orderId) {
        return shippingStatus.getOrDefault(orderId, "BELUM DIKIRIM / ORDER TIDAK DITEMUKAN");
    }
}
