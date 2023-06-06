package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.repositories.CleaningHistoryRepository;
import com.hotel.jorvik.repositories.RoomRepository;
import com.hotel.jorvik.repositories.RoomReservationRepository;
import com.hotel.jorvik.repositories.RoomTypeRepository;
import com.hotel.jorvik.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomReservationRepository roomReservationRepository;
    private final CleaningHistoryRepository cleaningHistoryRepository;

    @Override
    public Room getById(int id) {
        return null;
    }

    @Override
    public Iterable<Room> getAllByAvailableTime(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(from);
            formatter.parse(to);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return roomRepository.findAvailableRoomsByTime(from, to);
    }

    @Override
    public boolean isRoomAvailable(int roomId, String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(from);
            formatter.parse(to);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return roomRepository.isRoomAvailable(roomId, from, to);
    }

    @Override
    public Iterable<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(String from, String to, int roomOccupancy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(from);
            formatter.parse(to);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return roomTypeRepository.findAvailableRoomTypesByOccupancy(from, to, roomOccupancy);
    }
}
