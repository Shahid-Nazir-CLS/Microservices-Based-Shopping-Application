package com.example.payment.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "txn_amount")
    private Integer txnAmount;

    @Column(name = "service", length = 50)
    private String service;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "description", length = 255)
    private String description;


    public Payment() {
    }

    public Payment(Integer paymentId, Timestamp time, Integer txnAmount, String service, Integer userId, Integer orderId, String status, String description) {
        this.paymentId = paymentId;
        this.time = time;
        this.txnAmount = txnAmount;
        this.service = service;
        this.userId = userId;
        this.orderId = orderId;
        this.status = status;
        this.description = description;
    }

    public Integer getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getTxnAmount() {
        return this.txnAmount;
    }

    public void setTxnAmount(Integer txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}