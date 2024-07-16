package com.shoppy.cart.repository;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoppy.cart.model.Cart;

@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {

    // method for searching items in search fieled which match keyword
    @Query("SELECT i FROM Cart i WHERE i.customerId = :userId")
    Cart findByUserId(@Param("userId") int userId);
}
