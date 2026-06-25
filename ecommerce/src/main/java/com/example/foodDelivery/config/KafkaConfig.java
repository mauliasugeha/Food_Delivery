package com.example.foodDelivery.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    // ================= PRODUCER =================

    @Bean
    // 1. Ubah OrderEvent menjadi Object di sini
    public ProducerFactory<String, Object> producerFactory() {

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(JsonSerializer.TYPE_MAPPINGS,
                "orderEvent:com.example.foodDelivery.event.OrderEvent," +
                        "productReservedEvent:com.example.foodDelivery.event.ProductReservedEvent");

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    // 2. Ubah OrderEvent menjadi Object di sini
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

//    // ================= PRODUCER =================
//
//    @Bean
//    public ProducerFactory<String, OrderEvent> producerFactory() {
//
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:9092"
//        );
//
//        config.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class
//        );
//
//        config.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JsonSerializer.class
//        );
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate<String, OrderEvent> kafkaTemplate() {
//
//        return new KafkaTemplate<>(producerFactory());
//    }

    // ================= CONSUMER =================

    @Bean
    // 1. Ubah OrderEvent menjadi Object di sini
    public ConsumerFactory<String, Object> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "group1"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                ErrorHandlingDeserializer.class
        );
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.TYPE_MAPPINGS,
                "orderEvent:com.example.foodDelivery.event.OrderEvent," +
                        "productReservedEvent:com.example.foodDelivery.event.ProductReservedEvent");

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    // 3. Ubah OrderEvent menjadi Object di sini
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L)));

        return factory;
    }

    // ================= TOPICS =================

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("order-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentSuccessTopic() {
        return new NewTopic("payment-success-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return new NewTopic("payment-failed-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic releaseStockTopic() {
        return new NewTopic("release-stock-topic", 1, (short) 1);
    }

    // 4. Tambahkan topik baru ini untuk jembatan Inventory ke Payment!
    @Bean
    public NewTopic productReservedTopic() {
        return new NewTopic("product-reserved-topic", 1, (short) 1);
    }
}
