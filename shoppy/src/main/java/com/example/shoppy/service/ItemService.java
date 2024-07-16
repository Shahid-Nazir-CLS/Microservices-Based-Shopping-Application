package com.example.shoppy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shoppy.dto.ItemDTO;
import com.example.shoppy.dto.ItemsResponseDTO;

@Service
public class ItemService {
    @Autowired
    private RestTemplate restTemplate;

    public ItemsResponseDTO getItems(String pageNumber) {
        return restTemplate.getForObject("http://localhost:8081/api/v1/items?page=" + pageNumber, ItemsResponseDTO.class);
    }

    public List<ItemDTO> searchItems(String keyword) {
        ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(
                "http://localhost:8081/api/v1/items/search?keyword=" + keyword,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemDTO>>() {}
        );
        return response.getBody();
    }

    public ItemDTO getItemDetails(String itemId) {
        return restTemplate.getForObject("http://localhost:8081/api/v1/items/" + itemId, ItemDTO.class);
    }
}
