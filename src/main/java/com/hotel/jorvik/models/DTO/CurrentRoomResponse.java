package com.hotel.jorvik.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentRoomResponse {
    private int number;
    private int accessCode;
    String datePeriod;
}
