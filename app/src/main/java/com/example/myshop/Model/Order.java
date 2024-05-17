package com.example.myshop.Model;

public class Order {
    private String orderId;
    private String orderTime;
    private String orderDate;

    public Order() {

    }

    public Order(String orderId, String orderTime, String orderDate) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
    }

    // Getters и Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}