//package com.example.foodDelivery.producer;
//
//import com.example.foodDelivery.event.ProductReservedEvent;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProductProducer {
//
//    private final KafkaTemplate<String, ProductReservedEvent> kafkaTemplate;
//
//    public ProductProducer(KafkaTemplate<String, ProductReservedEvent> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendProductReserved(ProductReservedEvent event) {
//        // Mengirimkan event ke topik Kafka
//        kafkaTemplate.send("product-reserved-topic", event);
//        System.out.println("✅ EVENT PUBLISHED: Product Reserved untuk Order " + event.getOrderId());
//    }
//
//    // (Opsional) Tambahkan sendProductReservationFailed jika stok habis
//}