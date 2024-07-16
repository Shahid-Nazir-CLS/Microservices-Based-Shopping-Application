package com.example.shoppy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.shoppy.dto.AddressDTO;
import com.example.shoppy.dto.CustomerDTO;

@Service
public class CustomerService {
    @Autowired
    private RestTemplate restTemplate;

    public CustomerDTO getCustomer(int customerId) {
        return restTemplate.getForObject("http://localhost:8082/api/v1/customers/" + customerId, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        return restTemplate.postForObject("http://localhost:8082/api/v1/customers/", customerDTO, CustomerDTO.class);
    }

    public AddressDTO getAddress(int addressId) {
        return restTemplate.getForObject("http://localhost:8082/api/v1/addresses/" + addressId, AddressDTO.class);
    }

    public void deleteAddress(int addressId) {
        restTemplate.exchange("http://localhost:8082/api/v1/addresses/" + addressId, HttpMethod.DELETE, null, Void.class);
    }

    public void setDefaultAddress(int userId, int addressId) {
        restTemplate.exchange("http://localhost:8082/api/v1/customers/" + userId + "/address/" + addressId, HttpMethod.PUT, null, Void.class);
    }

    public void updateAddress(AddressDTO addressDTO){
        restTemplate.postForObject("http://localhost:8082/api/v1/addresses/", addressDTO, AddressDTO.class);
    }
}
