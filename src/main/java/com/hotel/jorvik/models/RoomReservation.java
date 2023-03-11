package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "RoomReservation")
public class RoomReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @FutureOrPresent
    @Column(name = "fromDate", nullable = false)
    private Date fromDate;

    @NotNull
    @Future
    @Column(name = "toDate", nullable = false)
    private Date toDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomNumber", referencedColumnName = "number")
    private Room room;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentID", referencedColumnName = "ID")
    private Payment payment;

    public RoomReservation() {
    }

    public RoomReservation(Date fromDate, Date toDate, Room room, User user, Payment payment) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.room = room;
        this.user = user;
        this.payment = payment;
    }
}
