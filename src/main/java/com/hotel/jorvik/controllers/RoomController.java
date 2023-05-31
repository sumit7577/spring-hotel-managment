package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor

public class RoomController {

    private final RoomService roomService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        Room room = roomService.getById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(room));
    }
    @GetMapping("/available/{dateFrom}/{dateTo}")
    public ResponseEntity<Response> getAllByAvailableTime(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
        Iterable<Room> rooms = roomService.getAllByAvailableTime(dateFrom, dateTo);
        if (!rooms.iterator().hasNext()) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("Weekends not found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(rooms));
    }

    @GetMapping("/available/{id}/{dateFrom}/{dateTo}")
    public ResponseEntity<Response> getRoomAvailability(
            @PathVariable int id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
        return ResponseEntity.ok().body(new SuccessResponse<>(roomService.isRoomAvailable(id, dateFrom, dateTo)));
    }

    @GetMapping("/available-room-type/{dateFrom}/{dateTo}/{roomOccupancy}")
    public ResponseEntity<Response> getAllRoomTypesByAvailabilityAndOccupancy(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
            @PathVariable int roomOccupancy) {
        Iterable<RoomType> roomTypes = roomService.getAllRoomTypesByAvailabilityAndOccupancy(dateFrom, dateTo, roomOccupancy);
        if (!roomTypes.iterator().hasNext()) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("No room types found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(roomTypes));
    }
}
