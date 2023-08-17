package com.hotel.jorvik.models.DTO.bookings;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomBookingResponse {
    @Positive
    private int number;

    @Positive
    private int accessCode;

    public RoomBookingResponse(int number, int accessCode) {
        this.number = number;
        this.accessCode = accessCode;
    }
}
