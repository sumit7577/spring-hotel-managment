package com.hotel.managment.models.dto.bookings;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Represents a response containing details of a room booking, including room number and access
 * code. This class is typically used to provide concise information about a room booking result.
 */
@Data
public class RoomBookingResponse {
  @Positive private int number;

  @Positive private int accessCode;

  public RoomBookingResponse(int number, int accessCode) {
    this.number = number;
    this.accessCode = accessCode;
  }
}
