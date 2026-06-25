package com.example.foodDelivery.consumer;

import com.example.foodDelivery.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

// Moved to notification-service.
public class EmailConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(EmailConsumer.class);

    // ================= SUCCESS EMAIL =================

    @KafkaListener(
            topics = "payment-success-topic",
            groupId = "email-success-group"
    )
    public void receiveSuccessEmail(OrderEvent order) {

        log.info("📧 EMAIL SERVICE - PAYMENT SUCCESS");

        try {

            Thread.sleep(1000);

            log.info("===========================================");
            log.info("📧 EMAIL PEMBAYARAN BERHASIL");
            log.info("===========================================");
            log.info("To: customer@example.com");
            log.info("Subject: Payment Success - {}", order.getOrderId());
            log.info("");
            log.info("Dear {},", order.getCustomerName());
            log.info("");
            log.info("Pembayaran berhasil diterima.");
            log.info("Order sedang diproses pengiriman.");
            log.info("");
            log.info("Order ID: {}", order.getOrderId());
            log.info("Produk: {}", order.getProductName());
            log.info("===========================================");

        } catch (Exception e) {

            log.error("❌ Gagal kirim email sukses: {}",
                    e.getMessage());
        }
    }

    // ================= FAILED EMAIL =================

    @KafkaListener(
            topics = "payment-failed-topic",
            groupId = "email-failed-group"
    )
    public void receiveFailedEmail(OrderEvent order) {

        log.info("📧 EMAIL SERVICE - PAYMENT FAILED");

        try {

            Thread.sleep(1000);

            log.info("===========================================");
            log.info("❌ EMAIL PEMBATALAN ORDER");
            log.info("===========================================");
            log.info("To: customer@example.com");
            log.info("Subject: Payment Failed - {}", order.getOrderId());
            log.info("");
            log.info("Dear {},", order.getCustomerName());
            log.info("");
            log.info("Maaf, pembayaran order gagal.");
            log.info("Order dibatalkan otomatis.");
            log.info("");
            log.info("Order ID: {}", order.getOrderId());
            log.info("Produk: {}", order.getProductName());
            log.info("Status: CANCELLED");
            log.info("===========================================");

        } catch (Exception e) {

            log.error("❌ Gagal kirim email gagal: {}",
                    e.getMessage());
        }
    }
}
