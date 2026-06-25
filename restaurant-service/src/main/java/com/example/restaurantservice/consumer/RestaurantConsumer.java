package com.example.restaurantservice.consumer;

import com.example.restaurantservice.event.OrderEvent;
import com.example.restaurantservice.event.ProductReservedEvent;
import com.example.restaurantservice.model.Product;
import com.example.restaurantservice.producer.ProductProducer;
import com.example.restaurantservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RestaurantConsumer {
    private static final Logger log = LoggerFactory.getLogger(RestaurantConsumer.class);

    private final ProductProducer productProducer;
    private final ProductRepository productRepository;

    public RestaurantConsumer(ProductProducer productProducer, ProductRepository productRepository) {
        this.productProducer = productProducer;
        this.productRepository = productRepository;
    }

    @KafkaListener(topics = "order-topic", groupId = "restaurant-group")
    public void receiveOrder(OrderEvent order) {
        log.info("[RESTAURANT] Order masuk: {} - {} x{}", order.getOrderId(), order.getProductName(), order.getQuantity());
        checkStock(order);
        updateStock(order);

        ProductReservedEvent reservedEvent = new ProductReservedEvent(
                order.getOrderId(),
                order.getTotalPrice(),
                order.getCustomerName(),
                order.getProductName(),
                order.getQuantity()
        );
        productProducer.sendProductReserved(reservedEvent);
        log.info("[RESTAURANT] Stok reserved untuk order {}", order.getOrderId());
    }

    @KafkaListener(topics = "payment-failed-topic", groupId = "restaurant-rollback-group")
    public void rollbackStock(OrderEvent failedOrder) {
        log.warn("[RESTAURANT] SAGA ROLLBACK untuk order {}", failedOrder.getOrderId());
        productRepository.findByName(failedOrder.getProductName()).ifPresentOrElse(product -> {
            product.setStock(product.getStock() + failedOrder.getQuantity());
            productRepository.save(product);
            log.info("[RESTAURANT] Stok {} dikembalikan sebanyak {}", failedOrder.getProductName(), failedOrder.getQuantity());
        }, () -> log.error("[RESTAURANT] Produk {} tidak ditemukan untuk rollback", failedOrder.getProductName()));
    }

    private void checkStock(OrderEvent order) {
        Product product = productRepository.findByName(order.getProductName())
                .orElseThrow(() -> new IllegalStateException("Produk " + order.getProductName() + " tidak ditemukan"));
        if (product.getStock() < order.getQuantity()) {
            throw new IllegalStateException("Stok " + order.getProductName() + " tidak cukup");
        }
    }

    private void updateStock(OrderEvent order) {
        Product product = productRepository.findByName(order.getProductName())
                .orElseThrow(() -> new IllegalStateException("Produk " + order.getProductName() + " tidak ditemukan"));
        product.setStock(product.getStock() - order.getQuantity());
        productRepository.save(product);
    }
}
