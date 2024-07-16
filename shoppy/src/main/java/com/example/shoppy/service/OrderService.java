package com.example.shoppy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shoppy.dto.OrderDTO;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    public OrderDTO cancelOrder(int orderId) {
        ResponseEntity<OrderDTO> response = restTemplate.exchange("http://localhost:8083/api/v1/orders/cancel/" + orderId, HttpMethod.PUT, null, OrderDTO.class);
        return response.getBody();
    }

    public OrderDTO createOrder(int customerId) {
        return restTemplate.postForObject("http://localhost:8083/api/v1/orders/" + customerId, null, OrderDTO.class);
    }

    public List<OrderDTO> getCustomerOrders(int userId) {
        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange("http://localhost:8083/api/v1/orders/customer/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDTO>>() {}
        );
        return response.getBody();
    }

    public OrderDTO getOrderDetails(int orderId) {
        return restTemplate.getForObject("http://localhost:8083/api/v1/orders/" + orderId, OrderDTO.class);
    }
}
