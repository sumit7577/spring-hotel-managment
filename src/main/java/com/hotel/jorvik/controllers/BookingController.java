package com.hotel.jorvik.controllers;

import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @GetMapping("/room/{dateFrom}/{dateTo}/{roomTypeId}")
    public ResponseEntity<Response> bookRoom(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
            @PathVariable int roomTypeId) {
        return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.bookRoom(dateFrom, dateTo, roomTypeId)));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @GetMapping("/entertainment/{paymentType}/{dateFrom}/{dateTo}/{timeFrom}/{timeTo}/{entertainmentId}")
    public ResponseEntity<Response> bookEntertainment(
            @PathVariable String paymentType,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
            @PathVariable @DateTimeFormat(pattern = "HH-mm") String timeFrom,
            @PathVariable @DateTimeFormat(pattern = "HH-mm") String timeTo,
            @PathVariable int entertainmentId) {
        return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.bookEntertainment(paymentType, dateFrom, timeFrom, dateTo, timeTo, entertainmentId)));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @GetMapping("/room/getLastBooking")
    public ResponseEntity<Response> getLastBooking() {
        return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getLastBooking()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @GetMapping("/room/getAllCurrentRooms")
    public ResponseEntity<Response> getFirstTen() {
        return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getAllCurrentRooms()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getAll()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @DeleteMapping("/deleteBooking/{reservationId}")
    public ResponseEntity<Response> deleteBooking(@PathVariable int reservationId) {
        bookingService.deleteRoomReservation(reservationId);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    @DeleteMapping("/deleteEntertainmentBooking/{reservationId}")
    public ResponseEntity<Response> deleteEntertainmentBooking(@PathVariable int reservationId) {
        bookingService.deleteEntertainmentReservation(reservationId);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
