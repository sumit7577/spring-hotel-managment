package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Entertainment_Reservation")
public class EntertainmentReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "from_date", nullable = false)
    @FutureOrPresent
    private Timestamp dateFrom;

    @Column(name = "to_date", nullable = false)
    @FutureOrPresent
    private Timestamp dateTo;

    @Column(name = "booked_at", nullable = false)
    private Timestamp bookedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entertainment_id")
    private Entertainment entertainment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public EntertainmentReservation() {
    }

    public EntertainmentReservation(Timestamp dateFrom, Timestamp dateTo, Timestamp bookedAt, User user, Entertainment entertainment) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.bookedAt = bookedAt;
        this.user = user;
        this.entertainment = entertainment;
    }

    public EntertainmentReservation(Timestamp dateFrom, Timestamp dateTo, Timestamp bookedAt, User user, Entertainment entertainment, Payment payment) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.bookedAt = bookedAt;
        this.user = user;
        this.entertainment = entertainment;
        this.payment = payment;
    }
}
