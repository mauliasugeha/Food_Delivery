package com.example.foodDelivery.monitoring;

import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OrderMetrics {

    private final MeterRegistry meterRegistry;

    public OrderMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("order.queue.size", this, OrderMetrics::getQueueSize)
                .description("Current order queue size")
                .register(meterRegistry);
    }

    public void incrementOrderReceived() {
        meterRegistry.counter("order.received").increment();
    }

    public void incrementOrderProcessed() {
        meterRegistry.counter("order.processed").increment();
    }

    public void recordProcessingTime(long durationMs) {
        meterRegistry.timer("order.processing.time")
                .record(durationMs, TimeUnit.MILLISECONDS);
    }

    private double getQueueSize() {
        return 0;
    }
}
