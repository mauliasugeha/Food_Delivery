// package com.example.order_management.kafka;

// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// @Service
// public class ProductConsumer {

//     @KafkaListener(topics = "product-topic", groupId = "order-group")
//     public void listen(String message) {
//         System.out.println("🔥 Received message: " + message);
//     }
// }