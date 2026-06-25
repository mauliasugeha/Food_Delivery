package com.example.foodDelivery.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderEvent implements Serializable {

    private String orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private BigDecimal totalPrice;
    private String status; // CREATED / UPDATED / CANCELLED
    private LocalDateTime eventTime;
    private String restaurantName;
    // private BigDecimal price;
    private LocalDateTime orderDate;
    

    public OrderEvent() {}

    public OrderEvent(String orderId,
                      String customerName,
                      String restaurantName,
                      String productName,
                      int quantity,
                      BigDecimal totalPrice,
                      LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}