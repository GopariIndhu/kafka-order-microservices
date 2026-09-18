package com.example.kafka_demo.producer;

import com.example.kafka_demo.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {

        kafkaTemplate.send("orders-topic", order);

        System.out.println("Order sent: " + order);
    }
}