package com.hotel.jorvik.models.dto.bookings;

import com.hotel.jorvik.models.RoomReservation;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of bookings, including payment information, booking
 * type, dates, room details, and more. This class is typically used to provide a comprehensive
 * summary of various bookings in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllBookingsResponse {

  private Integer paymentId;
  private String bookingType;
  private String fromDate;
  private String toDate;
  private String name;
  private String description;
  private RoomReservation.BookingStatus bookingStatus;
  private Timestamp timestampFrom;
  private int id;
  private Integer roomTypeId;
  private int price;
  private Integer accessCode;
}
