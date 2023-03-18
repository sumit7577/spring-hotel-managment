package com.hotel.jorvik.models;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "Entertainment_Reservations")
public class EntertainmentReservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "Date is required")
    @Column(name = "date", nullable = false)
    @FutureOrPresent
    private Date date;

    @NotNull(message = "Time is required")
    @Column(name = "time", nullable = false)
    private Time time;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID")
    private User user;

    @NotNull(message = "Entertainment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entertainment_ID")
    private Entertainment entertainment;

    @NotNull(message = "Payment is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_ID")
    private Payment payment;

    public EntertainmentReservations() {
    }

    public EntertainmentReservations(Date date, Time time, User user, Entertainment entertainment, Payment payment) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.entertainment = entertainment;
        this.payment = payment;
    }
}
