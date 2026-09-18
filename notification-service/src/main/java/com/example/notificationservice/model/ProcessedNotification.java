package com.example.notificationservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processed_notifications")
public class ProcessedNotification {

    @Id
    private Long orderId;

    public ProcessedNotification() {
    }

    public ProcessedNotification(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}