package com.example.foodDelivery.producer;

import com.example.foodDelivery.event.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    // Ubah OrderEvent menjadi Object
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Ubah OrderEvent menjadi Object di parameter constructor
    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderEvent order) {
        kafkaTemplate.send("order-topic", order);
    }
}