package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @FutureOrPresent
    @Column(name = "date", nullable = false)
    private Timestamp date;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false)
    private int amount;

    public Payment() {
    }

    public Payment(Timestamp date, int amount) {
        this.date = date;
        this.amount = amount;
    }
}
