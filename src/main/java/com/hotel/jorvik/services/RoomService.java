package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;

import java.util.List;

public interface RoomService {
    Room getById(int id);
    Room add(Room room);
    void delete(int id);
    List<Room> getAll();
    List<Room> getAllByAvailableTimeAndType(String from, String to, int roomTypeId);
    boolean isRoomAvailable(int roomId, String from, String to);
    List<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(String from, String to, int roomOccupancy);
}
