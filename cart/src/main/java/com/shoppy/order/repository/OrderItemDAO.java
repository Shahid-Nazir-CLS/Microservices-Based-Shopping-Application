package com.shoppy.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppy.order.model.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

}