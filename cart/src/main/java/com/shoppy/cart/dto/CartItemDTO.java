package com.shoppy.cart.dto;

import com.shoppy.order.dto.ItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private int cartItemId;
    private int cart_id;
    private int quantity;
    private int price;
    private int itemId;
    private ItemDTO itemDTO;
}
