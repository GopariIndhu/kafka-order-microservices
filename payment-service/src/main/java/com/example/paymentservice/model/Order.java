package com.example.paymentservice.model;

public class Order {

    private Long orderId;
    private String product;
    private Integer quantity;
    private Double price;

    public Order() {
    }

    public Order(Long orderId, String product, Integer quantity, Double price) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}