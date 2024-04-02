package com.hotel.managment.services.implementation;

import com.hotel.managment.models.Room;
import com.hotel.managment.models.RoomType;
import com.hotel.managment.repositories.RoomRepository;
import com.hotel.managment.repositories.RoomTypeRepository;
import com.hotel.managment.services.RoomService;
import com.hotel.managment.util.Tools;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation for managing room-related services in the application.
 *
 * <p>This service provides methods for handling room operations such as retrieval, addition, and
 * deletion of rooms. It also includes functionalities for checking room availability, retrieving
 * rooms based on specific criteria, and managing room types and their prices.
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomTypeRepository roomTypeRepository;

  @Override
  public Room getById(int id) {
    Optional<Room> weekend = roomRepository.findById(id);
    if (weekend.isEmpty()) {
      throw new NoSuchElementException("Room not found");
    }
    return weekend.get();
  }

  @Override
  public Room add(Room room) {
    roomRepository.save(room);
    return room;
  }

  @Override
  public void delete(int id) {
    if (roomRepository.findById(id).isEmpty()) {
      throw new NoSuchElementException("Room not found");
    }
    roomRepository.deleteById(id);
  }

  @Override
  public List<Room> getAll() {
    return roomRepository.findAll();
  }

  @Override
  public List<Room> getAllByAvailableTimeAndType(String from, String to, int roomTypeId) {
    Date dateFromSql = Tools.parseDate(from);
    Date dateToSql = Tools.parseDate(to);
    return roomRepository.findAvailableRoomsByTimeAndType(dateFromSql, dateToSql, roomTypeId);
  }

  @Override
  public boolean isRoomAvailable(int roomId, String from, String to) {
    Date dateFromSql = Tools.parseDate(from);
    Date dateToSql = Tools.parseDate(to);
    return roomRepository.isRoomAvailable(roomId, dateFromSql, dateToSql);
  }

  @Override
  public List<RoomType> getAllRoomTypes() {
    return roomTypeRepository.findAll();
  }

  @Override
  public List<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(
      String from, String to, int roomOccupancy) {
    Date dateFromSql = Tools.parseDate(from);
    Date dateToSql = Tools.parseDate(to);
    return roomTypeRepository.findAvailableRoomTypesByOccupancy(
        dateFromSql, dateToSql, roomOccupancy);
  }

  @Override
  public List<Room> getAllRoomsByAvailability(String dateFrom, String dateTo) {
    Date dateFromSql = Tools.parseDate(dateFrom);
    Date dateToSql = Tools.parseDate(dateTo);
    return roomRepository.findAvailableRoomsByTime(dateFromSql, dateToSql);
  }

  @Override
  public void updatePrices(List<RoomType> roomTypes) {
    for (RoomType roomType : roomTypes) {
      Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(roomType.getId());
      if (roomTypeOptional.isEmpty()) {
        throw new NoSuchElementException("Room type not found");
      }
      RoomType roomTypeFromDb = roomTypeOptional.get();
      roomTypeFromDb.setPrice(roomType.getPrice());
      roomTypeRepository.save(roomTypeFromDb);
    }
  }
}
