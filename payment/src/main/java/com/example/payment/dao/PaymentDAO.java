package com.example.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.payment.model.Payment;

@Repository
public interface PaymentDAO extends JpaRepository<Payment, Integer> {
}