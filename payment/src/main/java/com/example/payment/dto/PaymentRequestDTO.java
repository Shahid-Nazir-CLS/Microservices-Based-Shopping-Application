package com.example.payment.dto;
import java.sql.Time;
import java.util.Objects;


public class PaymentRequestDTO {
    private int orderId;
    private int userId;
    private int txnAmount;
    private String service;
    private Time time;
    private int paymentId;
    private String status;
    private String description;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(int orderId, int userId, int txnAmount, String service, Time time, int paymentId, String status, String description) {
        this.orderId = orderId;
        this.userId = userId;
        this.txnAmount = txnAmount;
        this.service = service;
        this.time = time;
        this.paymentId = paymentId;
        this.status = status;
        this.description = description;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTxnAmount() {
        return this.txnAmount;
    }

    public void setTxnAmount(int txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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


    @Override
    public String toString() {
        return "{" +
            " orderId='" + getOrderId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", txnAmount='" + getTxnAmount() + "'" +
            ", service='" + getService() + "'" +
            ", time='" + getTime() + "'" +
            ", paymentId='" + getPaymentId() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

}
