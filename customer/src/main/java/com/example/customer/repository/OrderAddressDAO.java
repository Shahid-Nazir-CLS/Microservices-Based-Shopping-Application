package com.example.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.customer.model.OrderAddress;

@Repository
public interface OrderAddressDAO extends JpaRepository<OrderAddress, Integer> {
}