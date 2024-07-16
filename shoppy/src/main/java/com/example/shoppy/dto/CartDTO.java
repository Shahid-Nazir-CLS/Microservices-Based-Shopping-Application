package com.example.shoppy.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private int Id;
    private int customer_id;
    private List<CartItemDTO> cartDtoItems;
}
