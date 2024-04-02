package com.hotel.managment.services;

import com.hotel.managment.models.EntertainmentReservation;
import com.hotel.managment.models.Payment;
import com.hotel.managment.models.Room;
import com.hotel.managment.models.RoomReservation;
import com.hotel.managment.models.dto.bookings.AllBookingsResponse;
import com.hotel.managment.models.dto.bookings.CurrentRoomResponse;
import com.hotel.managment.models.dto.bookings.EntertainmentReservationResponse;
import com.hotel.managment.models.dto.bookings.RoomReservationsResponse;
import java.util.List;

/**
 * Interface for managing bookings in the application.
 *
 * <p>This interface provides methods for booking and managing room and entertainment reservations.
 * It includes functionalities for creating, retrieving, and deleting reservations, as well as
 * handling payments and administrative tasks related to bookings.
 */
public interface BookingService {
  RoomReservation bookRoom(String from, String to, int roomTypeId);

  RoomReservation bookRoomByAdmin(String dateFrom, String dateTo, int roomId, int userId);

  EntertainmentReservation bookEntertainment(
      String type,
      String dateFrom,
      String timeFrom,
      String dateTo,
      String timeTo,
      int entertainmentId);

  RoomReservation getRoomReservation(int reservationId);

  EntertainmentReservation getEntertainmentReservation(int reservationId);

  Room getLastBooking();

  List<AllBookingsResponse> getAll();

  List<AllBookingsResponse> getAllByAdmin(int userId);

  List<CurrentRoomResponse> getAllCurrentRooms();

  void addPaymentToRoomReservation(int roomReservationId, Payment payment);

  void addPaymentToEntertainmentReservation(int reservationId, Payment payment);

  void deleteUnpaidRoomReservations();

  void deleteUnpaidEntertainmentReservations();

  void deleteRoomReservation(int reservationId);

  void deleteRoomReservationByAdmin(int reservationId);

  void deleteEntertainmentReservation(int reservationId);

  void deleteEntertainmentReservationByAdmin(int reservationId);

  List<RoomReservationsResponse> getBookingsForPeriod(String dateFrom, String dateTo);

  List<EntertainmentReservationResponse> getEntertainmentBookingsForPeriod(
      String dateFrom, String dateTo);
}
