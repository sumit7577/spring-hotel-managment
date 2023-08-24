package com.hotel.jorvik.models;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Entertainment_Reservation")
public class EntertainmentReservation extends Reservation {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entertainment_id")
    private Entertainment entertainment;

    @Column(name = "from_date", nullable = false)
    @FutureOrPresent
    protected Timestamp dateFrom;

    @Column(name = "to_date", nullable = false)
    @FutureOrPresent
    protected Timestamp dateTo;

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
