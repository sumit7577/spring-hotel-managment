package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
}
