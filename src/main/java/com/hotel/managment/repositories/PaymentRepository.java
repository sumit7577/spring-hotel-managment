package com.hotel.managment.repositories;

import com.hotel.managment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Payment entities. Extends JpaRepository to provide standard CRUD
 * operations for handling payments.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {}
