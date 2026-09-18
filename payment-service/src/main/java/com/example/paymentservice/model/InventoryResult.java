package com.example.paymentservice.model;

public class InventoryResult {

    private Long orderId;
    private String product;
    private Integer quantity;
    private Double price;
    private String status;

    public InventoryResult() {
    }

    public InventoryResult(
            Long orderId,
            String product,
            Integer quantity,
            Double price,
            String status) {

        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}