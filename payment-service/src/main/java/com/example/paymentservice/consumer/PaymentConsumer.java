package com.example.paymentservice.consumer;

import com.example.paymentservice.event.OrderEvent;
import com.example.paymentservice.event.ProductReservedEvent;
import com.example.paymentservice.producer.PaymentProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
    private final PaymentProducer paymentProducer;
    private static String lastPaymentStatus = "BELUM ADA";

    public PaymentConsumer(PaymentProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    @KafkaListener(topics = "product-reserved-topic", groupId = "payment-group")
    public void processPayment(ProductReservedEvent reservedProduct) {
        OrderEvent order = new OrderEvent();
        order.setOrderId(reservedProduct.getOrderId());
        order.setCustomerName(reservedProduct.getCustomerName());
        order.setProductName(reservedProduct.getProductName());
        order.setQuantity(reservedProduct.getQuantity());
        order.setTotalPrice(reservedProduct.getAmount());

        if (order.getCustomerName().equalsIgnoreCase("FAILED")) {
            order.setStatus("CANCELLED");
            paymentProducer.sendPaymentFailed(order);
            lastPaymentStatus = "FAILED - Order " + order.getOrderId() + " dibatalkan karena pembayaran gagal";
            return;
        }

        order.setStatus("PAID");
        paymentProducer.sendPaymentSuccess(order);
        lastPaymentStatus = "PAID - Order " + order.getOrderId() + " berhasil dibayar";
    }

    public static String getLastPaymentStatus() {
        return lastPaymentStatus;
    }
}
