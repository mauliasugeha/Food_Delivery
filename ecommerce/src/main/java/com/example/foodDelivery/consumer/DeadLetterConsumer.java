//package com.example.foodDelivery.consumer;
//
//import com.example.foodDelivery.model.Order;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DeadLetterConsumer {
//
//    private static final Logger log =
//            LoggerFactory.getLogger(DeadLetterConsumer.class);
//
//    @KafkaListener(topics = "${kafka.topic.dlq}", groupId = "dlq-group")
//    public void handleDeadLetter(Order order) {
//
//        log.error("❌ Pesan masuk DLQ (Kafka)");
//        log.error("Order ID: {}",
//                order != null ? order.getOrderId() : "unknown");
//
//        // Kafka TIDAK punya x-death
//        log.error("⚠ Tidak ada metadata x-death di Kafka");
//
//        saveFailedOrder(order);
//        sendAlertToAdmin(order);
//    }
//
//    private void saveFailedOrder(Order order) {
//        log.info("Menyimpan order {} ke database untuk investigasi",
//                order != null ? order.getOrderId() : "unknown");
//    }
//
//    private void sendAlertToAdmin(Order order) {
//        log.warn("📧 Alert dikirim ke admin");
//    }
//}