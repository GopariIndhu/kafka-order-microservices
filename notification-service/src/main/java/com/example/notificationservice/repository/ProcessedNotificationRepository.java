package com.example.notificationservice.repository;

import com.example.notificationservice.model.ProcessedNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedNotificationRepository
        extends JpaRepository<ProcessedNotification, Long> {
}