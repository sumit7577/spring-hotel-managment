package com.hotel.jorvik.models.DTO;

import com.hotel.jorvik.models.RoomReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllBookingsResponse {

    String bookingType;
    String datePeriod;
    String name;
    String description;
    RoomReservation.BookingStatus bookingStatus;
    Timestamp dateFrom;
    int id;
    int price;
    Integer accessCode;
}
