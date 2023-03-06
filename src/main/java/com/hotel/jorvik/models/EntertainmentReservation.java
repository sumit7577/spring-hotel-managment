package com.hotel.jorvik.models;

import lombok.Data;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "EntertainmentReservation")
public class EntertainmentReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entertainmentID")
    private Entertainment entertainment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentID")
    private Payment payment;

    public EntertainmentReservation() {
    }

    public EntertainmentReservation(Date date, Time time, User user, Entertainment entertainment, Payment payment) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.entertainment = entertainment;
        this.payment = payment;
    }
}
