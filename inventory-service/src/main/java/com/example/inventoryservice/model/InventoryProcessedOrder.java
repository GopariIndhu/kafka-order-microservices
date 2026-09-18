package com.example.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_processed_orders")
public class InventoryProcessedOrder {

    @Id
    private Long orderId;

    public InventoryProcessedOrder() {
    }

    public InventoryProcessedOrder(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}