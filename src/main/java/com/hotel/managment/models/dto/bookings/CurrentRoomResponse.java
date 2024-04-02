package com.hotel.managment.models.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing information about the current room status, including room
 * number, access code, and date period. This class is typically used to provide a concise summary
 * of the current room status or availability.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentRoomResponse {
  private int number;
  private int accessCode;
  private String datePeriod;
}
