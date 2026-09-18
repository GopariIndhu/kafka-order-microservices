package com.example.paymentservice.model;

public class PaymentResult {

    private Long orderId;
    private String product;
    private Double amount;
    private String status;

    public PaymentResult() {
    }

    public PaymentResult(
            Long orderId,
            String product,
            Double amount,
            String status) {

        this.orderId = orderId;
        this.product = product;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentResult{" +
                "orderId=" + orderId +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}