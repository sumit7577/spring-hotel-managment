package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
