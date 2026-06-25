package com.example.restaurantservice.event;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductReservedEvent implements Serializable {
    private String orderId;
    private BigDecimal amount;
    private String customerName;
    private String productName;
    private int quantity;

    public ProductReservedEvent() {}

    public ProductReservedEvent(String orderId, BigDecimal amount, String customerName, String productName, int quantity) {
        this.orderId = orderId;
        this.amount = amount;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
