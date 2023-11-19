package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.EntertainmentReservation;
import com.hotel.jorvik.models.User;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for EntertainmentReservation entities. Extends JpaRepository to facilitate
 * database operations for entertainment reservations. Includes methods to find reservations by
 * user, those without payments, and reservations within a specific date range.
 */
public interface EntertainmentReservationRepository
    extends JpaRepository<EntertainmentReservation, Integer> {
  List<EntertainmentReservation> findAllByUser(User user);

  List<EntertainmentReservation> findAllByPaymentIsNull();

  List<EntertainmentReservation> findAllByDateFromBetweenOrDateToBetween(
      Date date, Date date1, Date date2, Date date3);
}
