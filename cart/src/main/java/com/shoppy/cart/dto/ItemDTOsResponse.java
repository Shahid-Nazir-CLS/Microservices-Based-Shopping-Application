package com.shoppy.cart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTOsResponse {
    private List<ItemDTO> itemDTOs;
}
