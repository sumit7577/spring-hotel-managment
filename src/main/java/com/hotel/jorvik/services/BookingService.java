package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.bookings.AllBookingsResponse;
import com.hotel.jorvik.models.DTO.bookings.CurrentRoomResponse;
import com.hotel.jorvik.models.EntertainmentReservation;
import com.hotel.jorvik.models.Payment;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomReservation;

import java.util.List;

public interface BookingService {
    RoomReservation bookRoom(String from, String to, int roomTypeId);
    EntertainmentReservation bookEntertainment(String type, String dateFrom, String timeFrom, String dateTo, String timeTo, int entertainmentId);
    RoomReservation getRoomReservation(int reservationId);
    Room getLastBooking();
    List<AllBookingsResponse> getAll();
    List<CurrentRoomResponse> getAllCurrentRooms();
    void addPaymentToRoomReservation(int roomReservationId, Payment payment);
    void deleteUnpaidRoomReservations();
    void deleteUnpaidEntertainmentReservations();
    void deleteRoomReservation(int reservationId);
    void deleteEntertainmentReservation(int reservationId);
}
