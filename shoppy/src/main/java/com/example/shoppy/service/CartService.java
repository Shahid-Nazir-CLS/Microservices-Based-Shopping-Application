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

    private static final String BASE_URL = "http://cart-service/api/v1/carts/";

    public CartDTO getUserCart(int userId) {
        return restTemplate.getForObject(BASE_URL + userId, CartDTO.class);
    }

    public CartItemDTO addItemToCart(int userId, int itemId) {
        return restTemplate.postForObject(BASE_URL + userId + "/items/" + itemId, null, CartItemDTO.class);
    }

    public void removeItemFromCart(int userId, int cartItemId) {
        restTemplate.exchange(BASE_URL + userId + "/items/" + cartItemId, HttpMethod.DELETE, null, Void.class);
    }

    public void clearCart(int userId) {
        restTemplate.exchange(BASE_URL + userId, HttpMethod.DELETE, null, Void.class);
    }

    public void updateQuantity(int userId, int cartItemId, int newQuantity) {
        restTemplate.exchange(BASE_URL + userId + "/items/" + cartItemId + "?newQuantity=" + newQuantity, HttpMethod.PUT, null, Void.class);
    }
}