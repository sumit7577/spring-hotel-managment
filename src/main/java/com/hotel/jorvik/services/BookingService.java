package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.bookings.AllBookingsResponse;
import com.hotel.jorvik.models.DTO.bookings.CurrentRoomResponse;
import com.hotel.jorvik.models.Payment;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomReservation;

import java.util.List;

public interface BookingService {
    RoomReservation bookRoom(String from, String to, int roomTypeId);
    RoomReservation getRoomReservation(int reservationId);
    Room getLastBooking();
    List<AllBookingsResponse> getAll();
    List<CurrentRoomResponse> getAllCurrentRooms();
    void addPaymentToRoomReservation(int roomReservationId, Payment payment);
    void deleteUnpaidRoomReservations();
    void deleteRoomReservation(int reservationId);
}
