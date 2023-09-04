package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> add(@RequestBody @Valid Room room) {
        Room newRoom = roomService.add(room);
        return ResponseEntity.ok().body(new SuccessResponse<>(newRoom));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Integer id) {
        roomService.delete(id);
        return ResponseEntity.ok().body(new SuccessResponse<>("Room deleted"));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll() {
        Iterable<Room> rooms = roomService.getAll();
        return ResponseEntity.ok().body(new SuccessResponse<>(rooms));
    }

    @GetMapping("/room-available/{id}/{dateFrom}/{dateTo}")
    public ResponseEntity<Response> getRoomAvailability(
            @PathVariable int id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
        return ResponseEntity.ok().body(new SuccessResponse<>(roomService.isRoomAvailable(id, dateFrom, dateTo)));
    }

    @GetMapping("/available/{dateFrom}/{dateTo}/{roomTypeId}")
    public ResponseEntity<Response> getAllByAvailableTimeAndType(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
            @PathVariable Integer roomTypeId) {
        List<Room> rooms = roomService.getAllByAvailableTimeAndType(dateFrom, dateTo, roomTypeId);
        return ResponseEntity.ok().body(new SuccessResponse<>(rooms));
    }

    @GetMapping("/all-room-type")
    public ResponseEntity<Response> getAllRoomTypes() {
        List<RoomType> roomTypes = roomService.getAllRoomTypes();
        return ResponseEntity.ok().body(new SuccessResponse<>(roomTypes));
    }

    @GetMapping("/available-room-type/{dateFrom}/{dateTo}/{roomOccupancy}")
    public ResponseEntity<Response> getAllRoomTypesByAvailabilityAndOccupancy(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
            @PathVariable int roomOccupancy) {
        List<RoomType> roomTypes = roomService.getAllRoomTypesByAvailabilityAndOccupancy(dateFrom, dateTo, roomOccupancy);
        return ResponseEntity.ok().body(new SuccessResponse<>(roomTypes));
    }

    @GetMapping("/available-rooms/{dateFrom}/{dateTo}")
    public ResponseEntity<Response> getAllRoomByAvailability(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
        List<Room> roomTypes = roomService.getAllRoomsByAvailability(dateFrom, dateTo);
        return ResponseEntity.ok().body(new SuccessResponse<>(roomTypes));
    }

    @PatchMapping("/update-price")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> updatePrice(@RequestBody List<RoomType> roomTypes) {
        roomService.updatePrices(roomTypes);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
