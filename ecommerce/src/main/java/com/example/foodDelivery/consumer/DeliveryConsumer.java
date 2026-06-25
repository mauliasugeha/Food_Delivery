package com.example.foodDelivery.consumer;

import com.example.foodDelivery.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;

// Moved to shipping-service.
public class DeliveryConsumer {

    @KafkaListener(
            topics = "payment-success-topic",
            groupId = "delivery-group"
    )
    public void receiveOrder(OrderEvent order) {

        if ("CANCELLED".equals(order.getStatus())) {

            System.out.println("=================================");
            System.out.println("🚫 DELIVERY DIBATALKAN");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("=================================");

            return;
        }

        System.out.println("=================================");
        System.out.println("🚚 DELIVERY SERVICE");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("=================================");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        order.setStatus("SHIPPED");

        System.out.println("=================================");
        System.out.println("✅ Order sedang dikirim");
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("=================================");
    }
}
