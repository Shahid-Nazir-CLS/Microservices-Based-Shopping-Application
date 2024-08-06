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

    private static final String BASE_CUSTOMER_URL = "http://api-gateway/api/v1/customers/";
    private static final String BASE_ADDRESS_URL = "http://api-gateway/api/v1/addresses/";

    public CustomerDTO getCustomer(int customerId) {
        return restTemplate.getForObject(BASE_CUSTOMER_URL + customerId, CustomerDTO.class);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return restTemplate.getForObject(BASE_CUSTOMER_URL + "email/" + email, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        return restTemplate.postForObject(BASE_CUSTOMER_URL, customerDTO, CustomerDTO.class);
    }

    public AddressDTO getAddress(int addressId) {
        return restTemplate.getForObject(BASE_ADDRESS_URL + addressId, AddressDTO.class);
    }

    public void deleteAddress(int addressId) {
        restTemplate.exchange(BASE_ADDRESS_URL + addressId, HttpMethod.DELETE, null, Void.class);
    }

    public void setDefaultAddress(int userId, int addressId) {
        restTemplate.exchange(BASE_CUSTOMER_URL + userId + "/address/" + addressId, HttpMethod.PUT, null, Void.class);
    }

    public void updateAddress(AddressDTO addressDTO){
        restTemplate.postForObject(BASE_ADDRESS_URL, addressDTO, AddressDTO.class);
    }
}