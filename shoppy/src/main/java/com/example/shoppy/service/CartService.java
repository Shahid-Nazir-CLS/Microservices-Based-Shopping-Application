package com.example.shoppy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shoppy.dto.CartDTO;
import com.example.shoppy.dto.CartItemDTO;

@Service
public class CartService {
    @Autowired
    private RestTemplate restTemplate;

    public CartDTO getUserCart(int userId) {
        return restTemplate.getForObject("http://localhost:8083/api/v1/carts/" + userId, CartDTO.class);
    }

    public CartItemDTO addItemToCart(int userId, int itemId) {
        return restTemplate.postForObject("http://localhost:8083/api/v1/carts/" + userId + "/items/" + itemId, null, CartItemDTO.class);
    }

    public void removeItemFromCart(int userId, int cartItemId) {
        restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId + "/items/" + cartItemId, HttpMethod.DELETE, null, Void.class);
    }

    public void clearCart(int userId) {
        restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId, HttpMethod.DELETE, null, Void.class);
    }

    public void updateQuantity(int userId, int cartItemId, int newQuantity) {
        restTemplate.exchange("http://localhost:8083/api/v1/carts/" + userId + "/items/" + cartItemId + "?newQuantity=" + newQuantity, HttpMethod.PUT, null, Void.class);
    }
}
