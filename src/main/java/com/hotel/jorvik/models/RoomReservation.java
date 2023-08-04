package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Room_Reservation")
public class RoomReservation {

    public enum BookingStatus {
        ACTIVE,
        UPCOMING,
        COMPLETED,
        AWAITING_PAYMENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @FutureOrPresent
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Future
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Column(name = "booked_at", nullable = false)
    private Timestamp bookedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;


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
