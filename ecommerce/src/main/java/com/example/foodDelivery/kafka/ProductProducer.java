//package com.example.foodDelivery.kafka;
//
//import com.example.foodDelivery.event.OrderEvent;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProductProducer {
//
//    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
//
//    public ProductProducer(
//            KafkaTemplate<String, OrderEvent> kafkaTemplate
//    ) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void releaseStock(OrderEvent order) {
//
//        kafkaTemplate.send(
//                "release-stock-topic",
//                order
//        );
//    }
//}

package com.example.foodDelivery.kafka;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.event.ProductReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer {

    // Ubah tipe generic menjadi Object agar bisa mengirim OrderEvent & ProductReservedEvent
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // ==========================================
    // 1. HAPPY PATH: Mengirim event stok SUKSES dipotong
    // (Akan didengarkan oleh PaymentConsumer)
    // ==========================================
    public void sendProductReserved(ProductReservedEvent event) {
        kafkaTemplate.send("product-reserved-topic", event);
        System.out.println("✅ [PRODUCER] EVENT PUBLISHED: Product Reserved -> Lanjut Payment");
    }

    // ==========================================
    // 2. COMPENSATION PATH: Mengirim event KEMBALIKAN stok
    // (Kode asli milikmu)
    // ==========================================
    public void releaseStock(OrderEvent order) {
        kafkaTemplate.send("release-stock-topic", order);
        System.out.println("🔙 [PRODUCER] EVENT PUBLISHED: Release Stock (Kompensasi)");
    }
}