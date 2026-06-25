// package com.example.foodDelivery.config;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.amqp.core.*;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
// import org.springframework.amqp.support.converter.MessageConverter;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;

// import java.util.HashMap;
// import java.util.Map;

// @Configuration
// public class RabbitMQConfig {
//     @Bean
//     public SimpleRabbitListenerContainerFactory retryContainerFactory(
//             ConnectionFactory connectionFactory) {

//         SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

//         factory.setConnectionFactory(connectionFactory);
//         factory.setConcurrentConsumers(3);
//         factory.setMaxConcurrentConsumers(10);
//         factory.setPrefetchCount(1); // Satu pesan per consumer

//         factory.setMessageConverter(jsonMessageConverter());

//         factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

//         return factory;
//     }

//     private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

//     // Queue per service
//     @Value("${rabbitmq.queue.payment}")
//     private String paymentQueue;

//     @Value("${rabbitmq.queue.inventory}")
//     private String inventoryQueue;

//     @Value("${rabbitmq.queue.email}")
//     private String emailQueue;

//     @Value("${rabbitmq.queue.delivery}")
//     private String deliveryQueue;

//     // Fanout Exchange
//     @Value("${rabbitmq.exchange}")
//     private String fanoutExchange;

//     // Dead Letter Queue
//     @Value("${rabbitmq.dlq}")
//     private String deadLetterQueue;

//     @Value("${rabbitmq.dlx}")
//     private String deadLetterExchange;

//     @Value("${rabbitmq.dlq.routingkey}")
//     private String deadLetterRoutingKey;

//     // === QUEUES ===
//     @Bean
//     public Queue paymentQueue() {
//         Map<String, Object> args = new HashMap<>();
//         args.put("x-dead-letter-exchange", deadLetterExchange);
//         args.put("x-dead-letter-routing-key", deadLetterRoutingKey);

//         return new Queue(paymentQueue, true, false, false, args);
//     }

//     @Bean
//     public Queue inventoryQueue() {
//         Map<String, Object> args = new HashMap<>();
//         args.put("x-dead-letter-exchange", deadLetterExchange);
//         args.put("x-dead-letter-routing-key", deadLetterRoutingKey);

//         return new Queue(inventoryQueue, true, false, false, args);
//     }

//     @Bean
//     public Queue emailQueue() {
//         Map<String, Object> args = new HashMap<>();
//         args.put("x-dead-letter-exchange", deadLetterExchange);
//         args.put("x-dead-letter-routing-key", deadLetterRoutingKey);

//         return new Queue(emailQueue, true, false, false, args);
//     }

//     @Bean
//     public Queue orderPriorityQueue() {
//         Map<String, Object> args = new HashMap<>();
//         args.put("x-max-priority", 10); // max priority

//         return new Queue("order.priority.queue", true, false, false, args);
//     }

//     @Bean
//     public Queue deliveryQueue() {
//         return new Queue(deliveryQueue, true);
//     }


//     // === FANOUT EXCHANGE ===
//     @Bean
//     public FanoutExchange orderFanoutExchange() {
//         return new FanoutExchange(fanoutExchange);
//     }

//     // === BINDINGS ===
//     @Bean
//     public Binding paymentBinding() {
//         return BindingBuilder.bind(paymentQueue())
//                 .to(orderFanoutExchange());
//     }

//     @Bean
//     public Binding inventoryBinding() {
//         return BindingBuilder.bind(inventoryQueue())
//                 .to(orderFanoutExchange());
//     }

//     @Bean
//     public Binding emailBinding() {
//         return BindingBuilder.bind(emailQueue())
//                 .to(orderFanoutExchange());
//     }

//     @Bean
//     public Binding deliveryBinding() {
//         return BindingBuilder.bind(deliveryQueue()).to(orderFanoutExchange());
//     }

//     @Bean
//     public Binding priorityBinding() {
//         return BindingBuilder.bind(orderPriorityQueue())
//                 .to(orderFanoutExchange());
//     }

//     // === DEAD LETTER QUEUE ===
//     @Bean
//     public DirectExchange deadLetterExchange() {
//         return new DirectExchange(deadLetterExchange);
//     }

//     @Bean
//     public Queue deadLetterQueue() {
//         return new Queue(deadLetterQueue, true);
//     }

//     @Bean
//     public Binding deadLetterBinding() {
//         return BindingBuilder.bind(deadLetterQueue())
//                 .to(deadLetterExchange())
//                 .with(deadLetterRoutingKey);
//     }

//     // === MESSAGE CONVERTER ===
//     @Bean
//     public MessageConverter jsonMessageConverter() {
//         return new JacksonJsonMessageConverter();
//     }

//     // === RABBIT TEMPLATE ===
//     @Bean
//     public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//         RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//         rabbitTemplate.setMessageConverter(jsonMessageConverter());

//         rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//             if (ack) {
//                 log.info("Publisher confirm success: {}",
//                         correlationData != null ? correlationData.getId() : "unknown");
//             } else {
//                 log.error("Publisher confirm failed: {}", cause);
//             }
//         });

//         return rabbitTemplate;
//     }
// }