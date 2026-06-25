// package com.example.order_management.kafka;

// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class ProductProducer {

//     private final KafkaTemplate<String, String> kafkaTemplate;

//     public ProductProducer(KafkaTemplate<String, String> kafkaTemplate) {
//         this.kafkaTemplate = kafkaTemplate;
//     }

//     public void sendMessage(String message) {
//         kafkaTemplate.send("product-topic", message);
//     }
// }