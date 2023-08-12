package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.bookings.AllBookingsResponse;
import com.hotel.jorvik.models.DTO.bookings.CurrentRoomResponse;
import com.hotel.jorvik.models.Room;

import java.util.List;

public interface BookingService {
    Room bookRoom(String from, String to, int roomTypeId);
    Room getLastBooking();
    List<AllBookingsResponse> getAll();
    List<CurrentRoomResponse> getAllCurrentRooms();
}
