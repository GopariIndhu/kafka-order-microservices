package com.example.inventoryservice.consumer;

import com.example.inventoryservice.model.InventoryProcessedOrder;
import com.example.inventoryservice.model.InventoryResult;
import com.example.inventoryservice.model.Order;
import com.example.inventoryservice.model.Product;
import com.example.inventoryservice.repository.InventoryProcessedOrderRepository;
import com.example.inventoryservice.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryConsumer {

    private final ProductRepository productRepository;
    private final InventoryProcessedOrderRepository processedOrderRepository;
    private final KafkaTemplate<String, InventoryResult> kafkaTemplate;

    public InventoryConsumer(
            ProductRepository productRepository,
            InventoryProcessedOrderRepository processedOrderRepository,
            KafkaTemplate<String, InventoryResult> kafkaTemplate) {

        this.productRepository = productRepository;
        this.processedOrderRepository = processedOrderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @KafkaListener(
            topics = "orders-topic",
            groupId = "inventory-group"
    )
    public void consume(Order order) {

        System.out.println(
                "INVENTORY SERVICE RECEIVED: " + order
        );

        if (processedOrderRepository.existsById(order.getOrderId())) {

            System.out.println(
                    "Inventory already processed for order " +
                            order.getOrderId()
            );

            publishInventorySuccess(order);

            return;
        }

        Product product = productRepository
                .findByProductName(order.getProduct())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found: " +
                                        order.getProduct()
                        )
                );

        if (product.getStock() < order.getQuantity()) {

            System.out.println(
                    "Not enough stock for " +
                            order.getProduct()
            );

            return;
        }

        int oldStock = product.getStock();
        int newStock = oldStock - order.getQuantity();

        product.setStock(newStock);

        productRepository.save(product);

        InventoryProcessedOrder processedOrder =
                new InventoryProcessedOrder(order.getOrderId());

        processedOrderRepository.save(processedOrder);

        System.out.println(
                "Stock updated: " +
                        oldStock +
                        " -> " +
                        newStock
        );

        publishInventorySuccess(order);
    }

    private void publishInventorySuccess(Order order) {

        InventoryResult result =
                new InventoryResult(
                        order.getOrderId(),
                        order.getProduct(),
                        order.getQuantity(),
                        order.getPrice(),
                        "INVENTORY_RESERVED"
                );

        kafkaTemplate.send(
                "inventory-success-topic",
                result
        );

        System.out.println(
                "Inventory success event sent: " + result
        );
    }
}