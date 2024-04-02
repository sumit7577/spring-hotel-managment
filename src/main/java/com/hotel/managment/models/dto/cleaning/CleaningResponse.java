package com.hotel.managment.models.dto.cleaning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of cleaning assignments, including room ID, room number,
 * room type, and access code. This class is typically used to provide a summary of cleaning
 * assignments in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CleaningResponse {
  private int roomId;
  private int roomNumber;
  private int roomType;
  private int accessCode;
}
