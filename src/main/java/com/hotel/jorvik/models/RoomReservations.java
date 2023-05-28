package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Room_Reservation")
public class RoomReservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @FutureOrPresent
    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @NotNull
    @Future
    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    public RoomReservations() {
    }

    public RoomReservations(Date fromDate, Date toDate, Room room, User user, Payment payment) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.room = room;
        this.user = user;
        this.payment = payment;
    }
}
