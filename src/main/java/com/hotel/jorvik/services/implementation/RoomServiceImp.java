package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomReservation;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.repositories.RoomRepository;
import com.hotel.jorvik.repositories.RoomTypeRepository;
import com.hotel.jorvik.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(from);
            formatter.parse(to);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return roomRepository.findAvailableRoomsByTimeAndType(from, to, roomTypeId);
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
    public List<RoomType> getAllRoomTypesByAvailabilityAndOccupancy(String from, String to, int roomOccupancy) {
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
