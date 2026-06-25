package com.example.foodDelivery.producer;

import com.example.foodDelivery.event.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    // Ubah OrderEvent menjadi Object
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Ubah OrderEvent menjadi Object di parameter constructor ini
    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentSuccess(OrderEvent order) {
        kafkaTemplate.send("payment-success-topic", order);
    }

    public void sendPaymentFailed(OrderEvent order) {
        kafkaTemplate.send("payment-failed-topic", order);
    }
}