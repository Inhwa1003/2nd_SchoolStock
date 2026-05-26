package com.school.schoolstock.domain.order.vo;

import java.time.LocalDateTime;

public class Orders {

    private int orderNo;
    private String studentId;
    private int stockNo;
    private String orderContent; // BUY, SELL
    private int orderPoint;      // 한 주당 가격
    private int amount;
    private String orderState;   // PENDING, MATCHED, CANCELED
    private LocalDateTime orderDate;

    public Orders() {
    }

    public Orders(int orderNo, String studentId, int stockNo, String orderContent,
                   int orderPoint, int amount, String orderState, LocalDateTime orderDate) {
        this.orderNo = orderNo;
        this.studentId = studentId;
        this.stockNo = stockNo;
        this.orderContent = orderContent;
        this.orderPoint = orderPoint;
        this.amount = amount;
        this.orderState = orderState;
        this.orderDate = orderDate;
    }

    public Orders(String studentId, int stockNo, String orderContent,
                   int orderPoint, int amount) {
        this.studentId = studentId;
        this.stockNo = stockNo;
        this.orderContent = orderContent;
        this.orderPoint = orderPoint;
        this.amount = amount;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getStockNo() {
        return stockNo;
    }

    public void setStockNo(int stockNo) {
        this.stockNo = stockNo;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public int getOrderPoint() {
        return orderPoint;
    }

    public void setOrderPoint(int orderPoint) {
        this.orderPoint = orderPoint;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderNo=" + orderNo +
                ", studentId='" + studentId + '\'' +
                ", stockNo=" + stockNo +
                ", orderContent='" + orderContent + '\'' +
                ", orderPoint=" + orderPoint +
                ", amount=" + amount +
                ", orderState='" + orderState + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}