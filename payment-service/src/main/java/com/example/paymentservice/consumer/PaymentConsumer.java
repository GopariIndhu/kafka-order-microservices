package com.example.paymentservice.consumer;

import com.example.paymentservice.model.InventoryResult;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.PaymentResult;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentResult> kafkaTemplate;

    public PaymentConsumer(
            PaymentRepository paymentRepository,
            KafkaTemplate<String, PaymentResult> kafkaTemplate) {

        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "inventory-success-topic",
            groupId = "payment-group"
    )
    public void consume(InventoryResult inventoryResult) {

        System.out.println(
                "PAYMENT SERVICE RECEIVED: " +
                        inventoryResult.getOrderId()
        );

        if (paymentRepository.existsByOrderId(
                inventoryResult.getOrderId())) {

            System.out.println(
                    "Payment already exists for order " +
                            inventoryResult.getOrderId()
            );

            publishPaymentSuccess(inventoryResult);

            return;
        }

        double amount =
                inventoryResult.getPrice()
                        * inventoryResult.getQuantity();

        Payment payment =
                new Payment(
                        inventoryResult.getOrderId(),
                        amount,
                        "SUCCESS"
                );

        paymentRepository.save(payment);

        System.out.println(
                "Payment saved: ₹" +
                        amount +
                        " for order " +
                        inventoryResult.getOrderId()
        );

        publishPaymentSuccess(inventoryResult);
    }

    private void publishPaymentSuccess(
            InventoryResult inventoryResult) {

        double amount =
                inventoryResult.getPrice()
                        * inventoryResult.getQuantity();

        PaymentResult result =
                new PaymentResult(
                        inventoryResult.getOrderId(),
                        inventoryResult.getProduct(),
                        amount,
                        "PAYMENT_SUCCESS"
                );

        kafkaTemplate.send(
                "payment-success-topic",
                result
        );

        System.out.println(
                "Payment success event sent: " +
                        result
        );
    }
}