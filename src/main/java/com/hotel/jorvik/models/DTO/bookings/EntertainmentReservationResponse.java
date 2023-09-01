package com.hotel.jorvik.models.DTO.bookings;

import com.hotel.jorvik.models.RoomReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentReservationResponse {
    int reservationId;
    String clientName;
    String clientPhoneNumber;
    String datePeriod;
    String entertainmentType;
    String entertainmentElement;
    RoomReservation.BookingStatus bookingStatus;
    int rate;
}
