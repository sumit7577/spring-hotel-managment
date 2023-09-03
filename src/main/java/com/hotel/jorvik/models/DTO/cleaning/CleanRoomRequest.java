package com.hotel.jorvik.models.DTO.cleaning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CleanRoomRequest {
    int roomId;
}
