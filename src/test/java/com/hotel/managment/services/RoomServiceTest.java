package com.hotel.managment.services;

import com.hotel.managment.models.Room;
import com.hotel.managment.models.RoomType;
import com.hotel.managment.repositories.*;
import com.hotel.managment.services.implementation.RoomServiceImp;
import com.hotel.managment.util.Tools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

  @InjectMocks private RoomServiceImp roomService;
  @Mock private RoomRepository roomRepository;
  @Mock private RoomTypeRepository roomTypeRepository;

  @Test
  void getById() {
    Room room = new Room();
    room.setId(1);

    when(roomRepository.findById(1)).thenReturn(Optional.of(room));

    Room result = roomService.getById(1);

    assertEquals(room, result);
  }

  @Test
  void getByIdNotFound() {
    when(roomRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> roomService.getById(1));
  }

  @Test
  void add() {
    Room room = new Room();
    when(roomRepository.save(any(Room.class))).thenReturn(room);

    Room result = roomService.add(room);

    assertEquals(room, result);
  }

  @Test
  void delete() {
    Room room = new Room();
    room.setId(1);
    when(roomRepository.findById(1)).thenReturn(Optional.of(room));

    roomService.delete(1);

    verify(roomRepository, times(1)).deleteById(1);
  }

  @Test
  void getAll() {
    Room room1 = new Room(/* your properties here */ );
    Room room2 = new Room(/* your properties here */ );
    List<Room> rooms = Arrays.asList(room1, room2);

    when(roomRepository.findAll()).thenReturn(rooms);

    List<Room> result = roomService.getAll();

    assertEquals(rooms, result);
    verify(roomRepository, times(1)).findAll();
  }

  @Test
  void isRoomAvailable() {
    int roomId = 1;
    String from = "2023-01-01";
    String to = "2023-01-02";
    Date dateFromSql = Tools.parseDate(from);
    Date dateToSql = Tools.parseDate(to);

    when(roomRepository.isRoomAvailable(roomId, dateFromSql, dateToSql)).thenReturn(true);

    boolean result = roomService.isRoomAvailable(roomId, from, to);

    assertTrue(result);
    verify(roomRepository, times(1)).isRoomAvailable(roomId, dateFromSql, dateToSql);
  }

  @Test
  void getAllRoomTypes() {
    RoomType roomType1 = new RoomType();
    RoomType roomType2 = new RoomType();
    List<RoomType> roomTypes = Arrays.asList(roomType1, roomType2);

    when(roomTypeRepository.findAll()).thenReturn(roomTypes);

    List<RoomType> result = roomService.getAllRoomTypes();

    assertEquals(roomTypes, result);
    verify(roomTypeRepository, times(1)).findAll();
  }

  @Test
  void updatePrices() {
    RoomType roomType = new RoomType();
    roomType.setId(1);

    when(roomTypeRepository.findById(1)).thenReturn(Optional.of(roomType));

    List<RoomType> roomTypesToUpdate = List.of(roomType);
    roomService.updatePrices(roomTypesToUpdate);

    verify(roomTypeRepository, times(1)).save(roomType);
  }
}
