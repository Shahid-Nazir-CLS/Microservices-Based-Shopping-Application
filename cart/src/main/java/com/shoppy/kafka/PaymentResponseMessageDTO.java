package com.shoppy.kafka;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseMessageDTO {
    private int orderId;
    private int userId;
    private int txnAmount;
    private String service;
    private Time time;
    private int paymentId;
    private String status;
    private String description;
}
