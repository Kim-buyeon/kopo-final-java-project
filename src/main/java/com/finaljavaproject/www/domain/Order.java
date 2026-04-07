package com.finaljavaproject.www.domain;

import java.util.UUID;

public class Order {
    private String id;
    private String memberId;
    private String productId;
    private int quantity;

    public Order(String memberId, String productId, int quantity) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
