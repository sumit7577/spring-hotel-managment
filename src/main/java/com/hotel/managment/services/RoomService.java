package com.hotel.managment.services;

import com.hotel.managment.models.Room;
import com.hotel.managment.models.RoomType;
import java.util.List;

/**
 * Interface for managing room-related services in the application.
 *
 * <p>This interface provides methods for handling room operations such as retrieval, addition, and
 * deletion of rooms. It also includes functionalities for checking room availability, retrieving
 * rooms based on specific criteria, and managing room types and their prices.
 */
public interface RoomService {
  Room getById(int id);

  Room add(Room room);

  void delete(int id);

  List<Room> getAll();

  List<Room> getAllByAvailableTimeAndType(String from, String to, int roomTypeId);

  boolean isRoomAvailable(int roomId, String from, String to);

  List<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(
      String from, String to, int roomOccupancy);

  List<Room> getAllRoomsByAvailability(String dateFrom, String dateTo);

  List<RoomType> getAllRoomTypes();

  void updatePrices(List<RoomType> roomTypes);
}
