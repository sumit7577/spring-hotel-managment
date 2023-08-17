package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.RoomReservation;
import com.hotel.jorvik.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
    RoomReservation findFirstByUserOrderByBookedAtDesc(User user);
    List<RoomReservation> findAllByUser(User user);

    List<RoomReservation> findAllByPaymentIsNull();
}
