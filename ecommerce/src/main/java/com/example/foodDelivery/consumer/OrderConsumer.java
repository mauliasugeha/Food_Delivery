package com.example.foodDelivery.consumer;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.event.ProductReservedEvent;
import com.example.foodDelivery.kafka.ProductProducer;
import org.springframework.kafka.annotation.KafkaListener;

// Disabled because RestaurantConsumer already reserves stock against the database.
public class OrderConsumer {

    private final ProductProducer productProducer;

    public OrderConsumer(ProductProducer productProducer) {
        this.productProducer = productProducer;
    }

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(OrderEvent event) {
        System.out.println("📦 [INVENTORY] Memeriksa stok untuk produk: " + event.getProductName());
        System.out.println("📦 [INVENTORY] Jumlah pesanan: " + event.getQuantity());

        try {
            // ==========================================
            // LOGIKA POTONG STOK SUNGGUHAN DI SINI
            // (Simulasi sukses memotong stok)
            // ==========================================

            System.out.println("✅ [INVENTORY] Stok tersedia dan berhasil dipotong!");

            // Membuat event baru dan menyisipkan nama customer
            ProductReservedEvent reservedEvent = new ProductReservedEvent(
                    event.getOrderId(),
                    event.getTotalPrice(),
                    event.getCustomerName(),
                    event.getProductName(), //
                    event.getQuantity()// ⬅️ Ambil nama dari OrderEvent
            );

            productProducer.sendProductReserved(reservedEvent);

        } catch (Exception e) {
            System.out.println("❌ [INVENTORY] Stok gagal dipotong: " + e.getMessage());
            // Opsional: Kirim ProductReservationFailedEvent jika stok habis
        }
    }
}
