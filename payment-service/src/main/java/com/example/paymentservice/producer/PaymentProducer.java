package com.example.paymentservice.producer;

import com.example.paymentservice.event.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
