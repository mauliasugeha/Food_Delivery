package com.example.foodDelivery.consumer;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.event.ProductReservedEvent;
import com.example.foodDelivery.kafka.ProductProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.foodDelivery.model.Product;
import com.example.foodDelivery.repository.ProductRepository;

// Moved to restaurant-service.
public class RestaurantConsumer {

    private static final Logger log = LoggerFactory.getLogger(RestaurantConsumer.class);

    // Inject ProductProducer untuk mengirim event ke Payment
    private final ProductProducer productProducer;
    private final ProductRepository productRepository;

    // Simulasi database stok
//    private static final java.util.Map<String, Integer> menuStock = new java.util.HashMap<>();
//
//    static {
//        // Initial stock
//        menuStock.put("Nasi Goreng", 50);
//        menuStock.put("Mie Ayam", 40);
//        menuStock.put("Ayam Geprek", 30);
//        menuStock.put("Es Teh", 100);
//    }

    public RestaurantConsumer(ProductProducer productProducer, ProductRepository productRepository) {
        this.productProducer = productProducer;
        this.productRepository = productRepository;
    }

    /**
     * 1. SAGA HAPPY PATH: Listener untuk order baru
     */
    @KafkaListener(topics = "order-topic", groupId = "restaurant-group")
    public void receiveOrder(OrderEvent order) {
        log.info("===========================================");
        log.info("Order ID: {}", order.getOrderId());
        log.info("Produk: {}", order.getProductName());
        log.info("Quantity: {}", order.getQuantity());
        log.info("===========================================");

        try {
            checkStock(order);
            updateStock(order);
            log.info("✅ Restaurant berhasil memproses order: {}", order.getOrderId());

            ProductReservedEvent reservedEvent = new ProductReservedEvent(
                    order.getOrderId(), order.getTotalPrice(),
                    order.getCustomerName(), order.getProductName(), order.getQuantity()
            );
            productProducer.sendProductReserved(reservedEvent);
        }catch (IllegalStateException e) {
            // Stok tidak cukup
            log.error("❌ Stok tidak cukup: {}", e.getMessage());
            handleInsufficientStock(order);

        } catch (Exception e) {
            log.error("❌ Error saat update inventory: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 2. SAGA COMPENSATION (ROLLBACK): Kembalikan stok jika payment gagal
     */
    @KafkaListener(topics = "payment-failed-topic", groupId = "restaurant-rollback-group")
    public void rollbackStock(OrderEvent failedOrder) {
        log.warn("🔙 SAGA ROLLBACK: Mengembalikan stok untuk Order: {}", failedOrder.getOrderId());

        String productName = failedOrder.getProductName();
        int quantityToReturn = failedOrder.getQuantity();

        // 2. Ambil dari DB lalu kembalikan stoknya
        productRepository.findByName(productName).ifPresentOrElse(product -> {
            int currentStock = product.getStock();
            product.setStock(currentStock + quantityToReturn);
            productRepository.save(product); // Simpan kembali ke MySQL

            log.info("🔄 Stok {} dikembalikan sebanyak {}", productName, quantityToReturn);
            log.info("📈 Total stok {} kembali normal menjadi: {}", productName, product.getStock());
        }, () -> {
            log.error("❌ Gagal rollback: Produk {} tidak dikenali di database", productName);
        });
    }

    private void checkStock(OrderEvent order) {
        String productName = order.getProductName();
        int requestedQty = order.getQuantity();

        // 3. Cek stok langsung ke MySQL
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalStateException("Produk " + productName + " tidak ditemukan!"));

        log.info("Stok saat ini: {} porsi", product.getStock());
        log.info("Quantity diminta: {} porsi", requestedQty);

        if (product.getStock() < requestedQty) {
            throw new IllegalStateException("Stok tidak cukup!");
        }
        log.info("✅ Stok mencukupi");
    }

    private void updateStock(OrderEvent order) {
        String productName = order.getProductName();
        int quantity = order.getQuantity();

        // 4. Potong stok dari MySQL
        Product product = productRepository.findByName(productName).get();
        int currentStock = product.getStock();
        int newStock = currentStock - quantity;

        product.setStock(newStock);
        productRepository.save(product); // Simpan potongan ke MySQL

        log.info("📉 Stok diupdate:");
        log.info(" Produk: {}", productName);
        log.info(" Sebelum: {}", currentStock);
        log.info(" Setelah: {}", newStock);
        log.info(" Berkurang: {}", quantity);
    }

    /**
     * Handler jika stok tidak cukup
     */
    private void handleInsufficientStock(OrderEvent order) {
        log.warn("⚠ Menangani stok tidak cukup untuk order: {}", order.getOrderId());
        log.info("📧 Notifikasi dikirim ke admin untuk restock");
        log.info("📧 Email dikirim ke customer: {}", order.getCustomerName());

        // (Opsional) Jika di masa depan ingin membatalkan order saat stok habis,
        // kamu bisa memanggil productProducer untuk mengirimkan ProductReservationFailedEvent di sini.
    }

//    public static java.util.Map<String, Integer> getMenuStock() {
//        return new java.util.HashMap<>(menuStock);
//    }
}
