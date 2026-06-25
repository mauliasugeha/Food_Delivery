package com.example.foodDelivery.consumer;

import com.example.foodDelivery.event.OrderEvent;
import com.example.foodDelivery.event.ProductReservedEvent;
import com.example.foodDelivery.producer.PaymentProducer;
import org.springframework.kafka.annotation.KafkaListener;

// Moved to payment-service.
public class PaymentConsumer {

    private final PaymentProducer paymentProducer;

    private static String lastPaymentStatus = "BELUM ADA";

    public PaymentConsumer(PaymentProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    @KafkaListener(
            topics = "product-reserved-topic",
            groupId = "payment-group"
    )
    public void processPayment(ProductReservedEvent reservedProduct) {
        OrderEvent order = new OrderEvent();
        order.setOrderId(reservedProduct.getOrderId());
        order.setCustomerName(reservedProduct.getCustomerName());
        order.setProductName(reservedProduct.getProductName());
        order.setQuantity(reservedProduct.getQuantity());
        order.setTotalPrice(reservedProduct.getAmount());

        System.out.println("💳 PAYMENT SERVICE PROCESSING...");

        // ================= PAYMENT FAILED =================

        if (order.getCustomerName()
                .equalsIgnoreCase("FAILED")) {

            order.setStatus("CANCELLED");

            paymentProducer.sendPaymentFailed(order);

            lastPaymentStatus =
                    "FAILED - Order "
                            + order.getOrderId()
                            + " dibatalkan karena pembayaran gagal";

            System.out.println("=================================");
            System.out.println("❌ PAYMENT FAILED");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Reason: Payment gateway rejected transaction");
            System.out.println("=================================");

            return;
        }

        // ================= PAYMENT SUCCESS =================

        order.setStatus("PAID");

        paymentProducer.sendPaymentSuccess(order);

        lastPaymentStatus =
                "PAID - Order "
                        + order.getOrderId()
                        + " berhasil dibayar";

        System.out.println("=================================");
        System.out.println("💳 PEMBAYARAN BERHASIL");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("=================================");
    }

    public static String getLastPaymentStatus() {
        return lastPaymentStatus;
    }
}
