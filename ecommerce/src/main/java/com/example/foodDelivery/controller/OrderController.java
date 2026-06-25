package com.example.foodDelivery.controller;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.model.Order;
import com.example.foodDelivery.repository.OrderRepository;
import com.example.foodDelivery.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(
            OrderRepository orderRepository,
            OrderService orderService
    ) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }
    
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(
            @RequestBody OrderRequest request
    ) {

        // VALIDASI
        if (request.getQuantity() <= 0) {
            return ResponseEntity.badRequest()
                    .body("Quantity harus > 0");
        }

        if (request.getTotalPrice() == null ||
                request.getTotalPrice()
                .compareTo(BigDecimal.ZERO) <= 0) {

            return ResponseEntity.badRequest()
                    .body("Price harus > 0");
        }

        String orderId = UUID.randomUUID().toString();

        Order order = new Order(
                orderId,
                request.getCustomerName(),
                request.getRestaurantName(),
                request.getProductName(),
                request.getQuantity(),
                request.getTotalPrice()
        );

        orderRepository.save(order);

        OrderEvent event = new OrderEvent(
                orderId,
                order.getCustomerName(),
                order.getRestaurantName(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                LocalDateTime.now()
        );

        event.setStatus("CREATED");

        orderService.createOrder(event);

        return ResponseEntity.ok(
                "Order berhasil dibuat dengan ID: "
                        + orderId
        );
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderRepository.findAll()
        );
    }

    // =====================================
    // DTO REQUEST
    // =====================================
    public static class OrderRequest {

        private String customerName;
        private String restaurantName;
        private String productName;
        private int quantity;
        private BigDecimal totalPrice;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
