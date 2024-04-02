package com.hotel.managment.models.dto.bookings;

import com.hotel.managment.models.RoomReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of room reservations, including reservation ID, client
 * information, date period, room number, and status. This class is typically used to provide a
 * summarized view of room reservations in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationsResponse {
  private int reservationId;
  private String clientName;
  private String clientPhoneNumber;
  private String datePeriod;
  private int roomNumber;
  private RoomReservation.BookingStatus bookingStatus;
  private int rate;
}
