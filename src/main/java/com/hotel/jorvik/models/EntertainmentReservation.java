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

    @Column(name = "date", nullable = false)
    @FutureOrPresent
    private Timestamp date;

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

    public EntertainmentReservation(Timestamp date, Time time, User user, Entertainment entertainment, Payment payment) {
        this.date = date;
        this.user = user;
        this.entertainment = entertainment;
        this.payment = payment;
    }
}
