package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "amount", nullable = false)
    private int amount;

    public Payment() {
    }

    public Payment(Timestamp date, int amount) {
        this.date = date;
        this.amount = amount;
    }
}
