package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.CleaningHistory;
import com.hotel.jorvik.models.DTO.cleaning.CleaningResponse;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.repositories.CleaningHistoryRepository;
import com.hotel.jorvik.repositories.RoomRepository;
import com.hotel.jorvik.services.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CleaningServiceImp implements CleaningService {

    private final RoomRepository roomRepository;
    private final CleaningHistoryRepository cleaningHistoryRepository;

    @Override
    public List<CleaningResponse> getRoomsToClean() {
        List<CleaningResponse> cleaningResponses = new ArrayList<>();
        roomRepository.findActiveReservationRoomsNotCleanedToday().forEach(room -> {
            cleaningResponses.add(CleaningResponse.builder()
                    .roomId(room.getId())
                    .roomNumber(room.getNumber())
                    .roomType(room.getRoomType().getId())
                    .accessCode(room.getAccessCode())
                    .build(
            ));
        });
        return cleaningResponses;
    }

    @Override
    public void cleanRoom(int roomId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Room room = roomRepository.findById(roomId).orElseThrow();
        CleaningHistory cleaningHistory = new CleaningHistory(room, timestamp);
        cleaningHistoryRepository.save(cleaningHistory);
    }
}
