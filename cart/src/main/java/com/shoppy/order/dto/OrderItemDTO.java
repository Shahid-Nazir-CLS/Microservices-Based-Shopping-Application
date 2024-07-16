package com.shoppy.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shoppy.order.dto.ItemDTO;

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