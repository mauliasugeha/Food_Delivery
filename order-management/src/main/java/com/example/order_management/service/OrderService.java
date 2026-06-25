package com.example.order_management.service;

import com.example.order_management.entity.*;
import com.example.order_management.repository.*;
import com.example.order_management.kafka.OrderProducer;
import com.example.order_management.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProducer orderProducer;

    @Transactional
    public Order createOrder(Long customerId, List<ItemRequest> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new IllegalArgumentException("Order harus memiliki minimal 1 item");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer tidak ditemukan"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderNumber("ORD-" + System.currentTimeMillis());
        order.setStatus("PENDING"); // ⬅️ Status Awal PENDING
        order.setCreatedAt(LocalDateTime.now());

        double total = 0.0;
        Product firstProduct = null;

        for (ItemRequest req : itemRequests) {
            if (req.getProductId() == null) {
                throw new IllegalArgumentException("Product ID wajib diisi");
            }
            if (req.getQuantity() == null || req.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity harus lebih dari 0");
            }

            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product tidak ditemukan"));

            if(firstProduct == null) firstProduct = product;

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(req.getQuantity());
            item.setPrice(product.getPrice());
            item.setSubtotal(product.getPrice() * req.getQuantity());
            order.addItem(item);
            total += item.getSubtotal();
        }
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        if (firstProduct != null) {
            OrderEvent event = new OrderEvent();
            event.setOrderId(savedOrder.getOrderNumber());
            event.setCustomerName(customer.getName());
            event.setProductName(firstProduct.getName());
            event.setQuantity(itemRequests.get(0).getQuantity());
            event.setTotalPrice(BigDecimal.valueOf(total));
            event.setStatus("CREATED");

            orderProducer.sendOrder(event);
        }

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order updateStatus(Long orderId, String status) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElse(null);
    }

    public static class ItemRequest {
        private Long productId;
        private Integer quantity;
        public Long getProductId() {
            return productId;
        }
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        public Integer getQuantity() {
            return quantity;
        }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}


