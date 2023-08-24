package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Room_Reservation")
public class RoomReservation extends Reservation {

    public enum BookingStatus {
        ACTIVE,
        UPCOMING,
        COMPLETED,
        AWAITING_PAYMENT
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @FutureOrPresent
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Future
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    public RoomReservation() {
    }

    public RoomReservation(Date fromDate, Date toDate, Timestamp bookedAt, Room room, User user) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.bookedAt = bookedAt;
        this.room = room;
        this.user = user;
    }

    public RoomReservation(Date fromDate, Date toDate, Timestamp bookedAt, Room room, User user, Payment payment) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.bookedAt = bookedAt;
        this.room = room;
        this.user = user;
        this.payment = payment;
    }
}
