package com.hotel.jorvik.controllers;

import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the controller for managing bookings in the API. It handles various
 * booking-related operations such as room bookings, entertainment bookings, retrieval of bookings,
 * and deletion of bookings. Access to these operations is controlled by role-based authorization.
 */
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
    return ResponseEntity.ok()
        .body(new SuccessResponse<>(bookingService.bookRoom(dateFrom, dateTo, roomTypeId)));
  }

  /**
   * Books a room by an admin for a specific user within a specified date range.
   *
   * @param dateFrom The start date of the booking in the format "yyyy-MM-dd".
   * @param dateTo   The end date of the booking in the format "yyyy-MM-dd".
   * @param roomId   The ID of the room to be booked.
   * @param userId   The ID of the user for whom the room is being booked.
   * @return A ResponseEntity containing the booking response.
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/room-by-admin/{dateFrom}/{dateTo}/{roomId}/{userId}")
  public ResponseEntity<Response> bookRoomByAdmin(
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
      @PathVariable int roomId,
      @PathVariable int userId) {
    return ResponseEntity.ok()
        .body(
            new SuccessResponse<>(
                bookingService.bookRoomByAdmin(dateFrom, dateTo, roomId, userId)));
  }

  /**
   * Books an entertainment activity for a user within a specified date and time range.
   *
   * @param paymentType      The payment type for the entertainment booking.
   * @param dateFrom         The start date of the booking in the format "yyyy-MM-dd".
   * @param dateTo           The end date of the booking in the format "yyyy-MM-dd".
   * @param timeFrom         The start time of the booking in the format "HH-mm".
   * @param timeTo           The end time of the booking in the format "HH-mm".
   * @param entertainmentId  The ID of the entertainment activity to be booked.
   * @return A ResponseEntity containing the booking response.
   */
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @GetMapping(
      "/entertainment/{paymentType}/{dateFrom}/{dateTo}/{timeFrom}/{timeTo}/{entertainmentId}")
  public ResponseEntity<Response> bookEntertainment(
      @PathVariable String paymentType,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo,
      @PathVariable @DateTimeFormat(pattern = "HH-mm") String timeFrom,
      @PathVariable @DateTimeFormat(pattern = "HH-mm") String timeTo,
      @PathVariable int entertainmentId) {
    return ResponseEntity.ok()
        .body(
            new SuccessResponse<>(
                bookingService.bookEntertainment(
                    paymentType, dateFrom, timeFrom, dateTo, timeTo, entertainmentId)));
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @GetMapping("/room/getLastBooking")
  public ResponseEntity<Response> getLastBooking() {
    return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getLastBooking()));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/room/getBookingsForPeriod/{dateFrom}/{dateTo}")
  public ResponseEntity<Response> getBookingsForPeriod(
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
    return ResponseEntity.ok()
        .body(new SuccessResponse<>(bookingService.getBookingsForPeriod(dateFrom, dateTo)));
  }

  /**
   * Retrieves entertainment bookings for a specified date range.
   *
   * @param dateFrom The start date of the booking period in the format "yyyy-MM-dd".
   * @param dateTo   The end date of the booking period in the format "yyyy-MM-dd".
   * @return A ResponseEntity containing the list of entertainment bookings.
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/getEntertainmentBookingsForPeriod/{dateFrom}/{dateTo}")
  public ResponseEntity<Response> getEntertainmentBookingsForPeriod(
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateFrom,
      @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String dateTo) {
    return ResponseEntity.ok()
        .body(
            new SuccessResponse<>(
                bookingService.getEntertainmentBookingsForPeriod(dateFrom, dateTo)));
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @GetMapping("/room/getAllCurrentRooms")
  public ResponseEntity<Response> getAllCurrentRooms() {
    return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getAllCurrentRooms()));
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @GetMapping("/getAll")
  public ResponseEntity<Response> getAll() {
    return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getAll()));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @GetMapping("/getAllByAdmin/{userId}")
  public ResponseEntity<Response> getAllByAdmin(@PathVariable int userId) {
    return ResponseEntity.ok().body(new SuccessResponse<>(bookingService.getAllByAdmin(userId)));
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @DeleteMapping("/deleteBooking/{reservationId}")
  public ResponseEntity<Response> deleteBooking(@PathVariable int reservationId) {
    bookingService.deleteRoomReservation(reservationId);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping("/deleteBookingByAdmin/{reservationId}")
  public ResponseEntity<Response> deleteBookingByAdmin(@PathVariable int reservationId) {
    bookingService.deleteRoomReservationByAdmin(reservationId);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping("/deleteEntertainmentBookingByAdmin/{reservationId}")
  public ResponseEntity<Response> deleteEntertainmentBookingByAdmin(
      @PathVariable int reservationId) {
    bookingService.deleteEntertainmentReservationByAdmin(reservationId);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  @DeleteMapping("/deleteEntertainmentBooking/{reservationId}")
  public ResponseEntity<Response> deleteEntertainmentBooking(@PathVariable int reservationId) {
    bookingService.deleteEntertainmentReservation(reservationId);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
