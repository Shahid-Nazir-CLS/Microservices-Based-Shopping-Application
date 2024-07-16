package com.example.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.customer.model.Customer;

import jakarta.transaction.Transactional;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer> {

    @Query("SELECT a FROM Customer a WHERE a.email = :email")
    Customer findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.defaultAddress = :addressId WHERE c.customerId = :customerId")
    void setDefaultAddress(@Param("customerId") int customerId, @Param("addressId") Integer addressId);
}
