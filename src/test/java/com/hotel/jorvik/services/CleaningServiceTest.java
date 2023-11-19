package com.hotel.jorvik.services;

import com.hotel.jorvik.models.CleaningHistory;
import com.hotel.jorvik.models.dto.cleaning.CleaningResponse;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.repositories.CleaningHistoryRepository;
import com.hotel.jorvik.repositories.RoomRepository;
import com.hotel.jorvik.services.implementation.CleaningServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CleaningServiceTest {

  @InjectMocks private CleaningServiceImp cleaningService;

  @Mock private RoomRepository roomRepository;

  @Mock private CleaningHistoryRepository cleaningHistoryRepository;

  @Test
  void getRoomsToClean() {
    Room room1 = new Room(101, 123, new RoomType(10, 20, 30));
    Room room2 = new Room(102, 321, new RoomType(20, 30, 40));
    List<Room> rooms = Arrays.asList(room1, room2);

    when(roomRepository.findActiveReservationRoomsNotCleanedToday()).thenReturn(rooms);

    List<CleaningResponse> result = cleaningService.getRoomsToClean();

    assertEquals(2, result.size());
    assertEquals(101, result.get(0).getRoomNumber());
    assertEquals(102, result.get(1).getRoomNumber());
  }

  @Test
  void cleanRoom() {
    int roomId = 1;
    Room room = new Room(101, 123, new RoomType(10, 20, 30));

    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

    cleaningService.cleanRoom(roomId);

    ArgumentCaptor<CleaningHistory> captor = ArgumentCaptor.forClass(CleaningHistory.class);
    verify(cleaningHistoryRepository, times(1)).save(captor.capture());

    assertEquals(room, captor.getValue().getRoom());
  }

  @Test
  void cleanRoomNotFound() {
    int roomId = 1;

    when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> cleaningService.cleanRoom(roomId));
  }
}
