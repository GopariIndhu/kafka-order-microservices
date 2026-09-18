package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.InventoryProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProcessedOrderRepository
        extends JpaRepository<InventoryProcessedOrder, Long> {
}