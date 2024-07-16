package com.shoppy.order.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int orderId;
    private int customerId;
    private Timestamp orderDate;
    private String status;
    private int totalPrice;
    private List<OrderItemDTO> orderItems;
    private Integer addressId;
    private AddressDTO addressDTO;

    // Getters and Setters
}
