package com.hotel.jorvik.models.DTO.bookings;

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

    Integer paymentId;
    String bookingType;
    String fromDate;
    String toDate;
    String name;
    String description;
    RoomReservation.BookingStatus bookingStatus;
    Timestamp timestampFrom;
    int id;
    Integer roomTypeId;
    int price;
    Integer accessCode;
}
