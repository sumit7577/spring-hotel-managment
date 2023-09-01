package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.EntertainmentReservation;
import com.hotel.jorvik.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface EntertainmentReservationRepository extends JpaRepository<EntertainmentReservation, Integer> {
    List<EntertainmentReservation> findAllByUser(User user);
    List<EntertainmentReservation> findAllByPaymentIsNull();
    List<EntertainmentReservation> findAllByDateFromBetweenOrDateToBetween(Date date, Date date1, Date date2, Date date3);
}
