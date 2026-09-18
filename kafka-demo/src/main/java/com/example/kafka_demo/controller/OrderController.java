package com.example.kafka_demo.controller;

import com.example.kafka_demo.model.Order;
import com.example.kafka_demo.producer.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(
            @RequestBody Order order) {

        orderProducer.sendOrder(order);

        return ResponseEntity.ok(
                "Order sent to Kafka successfully"
        );
    }
}