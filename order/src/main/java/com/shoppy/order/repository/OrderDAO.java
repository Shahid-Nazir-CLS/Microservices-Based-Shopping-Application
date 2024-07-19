package com.shoppy.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppy.order.model.Order;

import jakarta.transaction.Transactional;

public interface OrderDAO extends JpaRepository<Order, Integer> {
    // method for searching items in search fieled which match keyword
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = 'CANCELLED' WHERE o.id = :orderId")
    void cancelOrder(@Param("orderId") int orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderId") int orderId, @Param("status") String status);

    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
    List<Order> findAllByCustomerId(@Param("customerId") int customerId);
}