package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Optional<Place> findByName(String name);
}
