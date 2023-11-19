package com.hotel.jorvik.models.dto.cleaning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to clean a room, specifying the room ID. This class is typically used to
 * request the cleaning of a specific room by providing its unique identifier.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CleanRoomRequest {
  private int roomId;
}
