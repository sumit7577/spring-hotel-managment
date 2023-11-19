package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.EntertainmentType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for EntertainmentType entities. Extends JpaRepository to provide standard
 * database operations. Includes a method to find an EntertainmentType by its name.
 */
public interface EntertainmentTypeRepository extends JpaRepository<EntertainmentType, Integer> {
  Optional<EntertainmentType> findByName(String paymentType);
}
