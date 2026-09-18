package com.example.notificationservice.consumer;

import com.example.notificationservice.model.PaymentResult;
import com.example.notificationservice.model.ProcessedNotification;
import com.example.notificationservice.repository.ProcessedNotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final ProcessedNotificationRepository notificationRepository;

    public NotificationConsumer(
            ProcessedNotificationRepository notificationRepository) {

        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(
            topics = "payment-success-topic",
            groupId = "notification-group-v3"
    )
    public void consume(PaymentResult paymentResult) {

        System.out.println(
                "NOTIFICATION SERVICE RECEIVED: " +
                        paymentResult
        );

        if (!"PAYMENT_SUCCESS".equals(paymentResult.getStatus())) {
            return;
        }

        if (notificationRepository.existsById(
                paymentResult.getOrderId())) {

            System.out.println(
                    "Notification already sent for order " +
                            paymentResult.getOrderId()
            );

            return;
        }

        System.out.println(
                "Sending confirmation for order " +
                        paymentResult.getOrderId() +
                        " - " +
                        paymentResult.getProduct() +
                        " - Payment ₹" +
                        paymentResult.getAmount()
        );

        notificationRepository.save(
                new ProcessedNotification(
                        paymentResult.getOrderId()
                )
        );
    }
}