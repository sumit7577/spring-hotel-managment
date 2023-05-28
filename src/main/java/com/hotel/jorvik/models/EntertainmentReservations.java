package com.hotel.jorvik.models;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Time;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Entertainment_Reservation")
public class EntertainmentReservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Date is required")
    @Column(name = "date", nullable = false)
    @FutureOrPresent
    private Timestamp date;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Entertainment is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entertainment_id")
    private Entertainment entertainment;

    @NotNull(message = "Payment is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public EntertainmentReservations() {
    }

    public EntertainmentReservations(Timestamp date, Time time, User user, Entertainment entertainment, Payment payment) {
        this.date = date;
        this.user = user;
        this.entertainment = entertainment;
        this.payment = payment;
    }
}
