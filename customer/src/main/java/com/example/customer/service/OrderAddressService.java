package com.example.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.dto.OrderAddressDTO;
import com.example.customer.model.OrderAddress;
import com.example.customer.repository.OrderAddressDAO;

@Service
public class OrderAddressService {

    @Autowired
    private OrderAddressDAO orderAddressDAO;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderAddress> getAllOrderAddresses() {
        return orderAddressDAO.findAll();
    }

    public OrderAddressDTO getOrderAddressById(int id) {
        return modelMapper.map(orderAddressDAO.getReferenceById(id), OrderAddressDTO.class);
    }

    public OrderAddressDTO saveOrderAddress(OrderAddressDTO orderAddressDto) {
        OrderAddress address = modelMapper.map(orderAddressDto, OrderAddress.class);
        return modelMapper.map(orderAddressDAO.save(address), OrderAddressDTO.class);
    }

    public void deleteOrderAddressById(int id) {
        orderAddressDAO.deleteById(id);
    }

    public OrderAddressDTO updateOrderAddress(OrderAddress orderAddress) {
        return modelMapper.map(orderAddressDAO.save(orderAddress), OrderAddressDTO.class);
    }
}