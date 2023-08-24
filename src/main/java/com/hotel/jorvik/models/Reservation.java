package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@MappedSuperclass
public abstract class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "booked_at", nullable = false)
    protected Timestamp bookedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    protected Payment payment;
}
