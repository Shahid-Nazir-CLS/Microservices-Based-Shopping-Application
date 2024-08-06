package com.example.shoppy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shoppy.dto.OrderDTO;
// import com.example.shoppy.kafka.KafkaProducerService;
import com.example.shoppy.kafka.KafkaProducerService;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    private static final String BASE_URL = "http://api-gateway/api/v1/orders/";

    public OrderDTO cancelOrder(int orderId) {
        ResponseEntity<OrderDTO> response = restTemplate.exchange(BASE_URL + "cancel/" + orderId, HttpMethod.PUT, null, OrderDTO.class);
        return response.getBody();
    }

    public void createOrder(int customerId) {
        // Send message to Kafka
        System.out.println("sent order");
        kafkaProducerService.sendMessage(new String(String.valueOf(customerId)));
    }

    public List<OrderDTO> getCustomerOrders(int userId) {
        ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(BASE_URL + "customer/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDTO>>() {}
        );
        return response.getBody();
    }

    public OrderDTO getOrderDetails(int orderId) {
        return restTemplate.getForObject(BASE_URL + orderId, OrderDTO.class);
    }
}