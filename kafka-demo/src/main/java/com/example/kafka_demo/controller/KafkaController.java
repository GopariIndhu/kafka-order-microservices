package com.example.kafka_demo.controller;

import com.example.kafka_demo.producer.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final MessageProducer messageProducer;

    public KafkaController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(
            @RequestParam String message) {

        messageProducer.sendMessage(message);

        return ResponseEntity.ok(
                "Message sent to Kafka: " + message
        );
    }
}
