package com.shoppy.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private int Id;
    @Size(max = 45)
    @NotEmpty
    private String name;

    @Size(max = 1000)
    private String description;

    @NotEmpty
    private int price;

    private int rating;

    @Size(max = 150)
    private String imageURL;

    @Size(max = 45)
    private String category;

    private int quantity;

    private int remainingQuantity;
}
