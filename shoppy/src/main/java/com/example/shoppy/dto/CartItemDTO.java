package com.example.shoppy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private int cartId;
    private int quantity;
    private int price;
    private int itemId;
    private int cartItemId;
    private ItemDTO itemDTO;
}