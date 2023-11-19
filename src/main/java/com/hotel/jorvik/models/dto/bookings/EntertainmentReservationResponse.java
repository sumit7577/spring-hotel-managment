package com.hotel.jorvik.models.dto.bookings;

import com.hotel.jorvik.models.RoomReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of an entertainment reservation, including reservation
 * ID, client information, date period, entertainment type, and status. This class is typically used
 * to provide a summarized view of entertainment reservations in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentReservationResponse {
  private int reservationId;
  private String clientName;
  private String clientPhoneNumber;
  private String datePeriod;
  private String entertainmentType;
  private String entertainmentElement;
  private RoomReservation.BookingStatus bookingStatus;
  private int rate;
}
