package com.shoppy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private int orderItemid;
    private int itemId;
    private int quantity;
    private int price;
    private ItemDTO itemDTO;
}