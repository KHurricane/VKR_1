package com.example.myshop.Model;

public class Order {
    private String orderId;
    private String orderTime;
    private String orderDate;
    private String orderDescription;
    private String totalAmount;
    private String address;

    public Order() {

    }

    public Order(String orderId, String orderTime, String orderDate, String orderDescription, String totalAmount,String address) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.orderDescription= orderDescription;
        this.totalAmount= totalAmount;
        this.address = address;
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

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}