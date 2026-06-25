package com.example.restaurantservice.producer;

import com.example.restaurantservice.event.OrderEvent;
import com.example.restaurantservice.event.ProductReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductReserved(ProductReservedEvent event) {
        kafkaTemplate.send("product-reserved-topic", event);
    }

    public void releaseStock(OrderEvent order) {
        kafkaTemplate.send("release-stock-topic", order);
    }
}
