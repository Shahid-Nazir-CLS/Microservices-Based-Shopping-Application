package com.shoppy.order.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestMessageDTO {
    private int orderId;
    private int userId;
    private int txnAmount;
    private String service;
}
