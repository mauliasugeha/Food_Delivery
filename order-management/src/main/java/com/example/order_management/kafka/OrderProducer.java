package com.example.order_management.kafka;

import com.example.order_management.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate; // Tipenya sudah sama

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderEvent order) {
        kafkaTemplate.send("order-topic", order);
        System.out.println("🚀 [ORDER-MANAGEMENT] Event berhasil dikirim: " + order.getOrderId());
    }
}