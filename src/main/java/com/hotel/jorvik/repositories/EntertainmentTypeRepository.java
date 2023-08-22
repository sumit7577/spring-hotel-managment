package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.EntertainmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Integer> {
    Optional<EntertainmentType> findByName(String paymentType);
}
