package com.example.customer.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.customer.dto.CustomerDTO;
import com.example.customer.model.Customer;
import com.example.customer.repository.CustomerDAO;

@Component
public class CustomerService {
    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ModelMapper modelMapper;

    // getItem
    public CustomerDTO getCustomer(int customerId) {
        // convert Item returned by addressDAO to itemDTO object using modelmapper
        return modelMapper.map(customerDAO.getReferenceById(customerId), CustomerDTO.class);
    }

    // setDefaultAddress
    public CustomerDTO setDefaultAddress(int customerId, Integer addressId) {
        customerDAO.setDefaultAddress(customerId, addressId);
        // convert Item returned by addressDAO to itemDTO object using modelmapper
        return getCustomer(customerId);
    }

    // save Customer
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        // Retrieve existing customer if it exists
        Customer customer = customerDAO.findById(customerDTO.getCustomerId())
                .orElse(new Customer());


        // Map fields from DTO to entity only if they are not null
        if (customerDTO.getEmail() != null) {
            customer.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getGoogleId() != null) {
            customer.setGoogleId(customerDTO.getGoogleId());
        }
        if (customerDTO.getUsername() != null) {
            customer.setUsername(customerDTO.getUsername());
        }
        if (customerDTO.getProfilePic() != null) {
            customer.setProfilePic(customerDTO.getProfilePic());
        }
        if (customerDTO.getDefaultAddress() != null) {
            customer.setDefaultAddress(customerDTO.getDefaultAddress());
        }
        // Save the customer entity
        Customer savedCustomer = customerDAO.save(customer);

        // Map saved entity back to DTO
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    public  CustomerDTO getCustomerByEmail(String email){
        Customer customer = customerDAO.findByEmail((email));
        System.out.println(customer);
        if(customer == null) return null;
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
