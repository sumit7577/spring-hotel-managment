package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Integer> {
}
