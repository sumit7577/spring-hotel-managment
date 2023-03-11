package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Weekend")
public class Weekend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date_from", nullable = false)
    private Timestamp dateFrom;

    @Column(name = "date_to", nullable = false)
    private Timestamp dateTo;

    public Weekend() {
    }

    public Weekend(String place, String description, Timestamp dateFrom, Timestamp dateTo) {
        this.place = place;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
