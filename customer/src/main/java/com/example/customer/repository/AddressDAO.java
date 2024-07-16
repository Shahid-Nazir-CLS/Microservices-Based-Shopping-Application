package com.example.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.customer.model.Address;

@Repository
public interface AddressDAO extends JpaRepository<Address, Integer> {

    // @Query("SELECT a FROM Address a WHERE a.customer_id = :customerId")
    // List<Address> findAllForUser(@Param("customerId") int customerId);

    @Query("SELECT a FROM Address a WHERE a.customerId = :customerId")
    List<Address> findAllForUser(@Param("customerId") int customerId);
}