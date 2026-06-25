package com.example.notificationservice.consumer;

import com.example.notificationservice.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "payment-success-topic", groupId = "email-success-group")
    public void receiveSuccessEmail(OrderEvent order) {
        log.info("[EMAIL] Pembayaran berhasil untuk order {} milik {}", order.getOrderId(), order.getCustomerName());
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "email-failed-group")
    public void receiveFailedEmail(OrderEvent order) {
        log.info("[EMAIL] Pembayaran gagal untuk order {} milik {}", order.getOrderId(), order.getCustomerName());
    }
}
