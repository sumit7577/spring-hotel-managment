package com.hotel.jorvik.models.DTO.bookings;

import com.hotel.jorvik.models.RoomReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationsResponse {
    int reservationId;
    String clientName;
    String clientPhoneNumber;
    String datePeriod;
    int roomNumber;
    RoomReservation.BookingStatus bookingStatus;
    int rate;
}
