package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;

public interface RoomService {
    Room getById(int id);
    Iterable<Room> getAllByAvailableTime(String from, String to);
    boolean isRoomAvailable(int roomId, String from, String to);
    Iterable<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(String from, String to, int roomOccupancy);
}
