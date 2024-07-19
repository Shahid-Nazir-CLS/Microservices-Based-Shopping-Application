package com.shoppy.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private int Id;
    private String name;
    private String description;
    private int price;
    private int rating;
    private String imageURL;
    private String category;
    private int quantity;
    private int remainingQuantity;
}
