package com.hotel.jorvik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Weekend")
public class Weekend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 500, message = "Description cannot be less that 3 and more than 500 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Start date is required")
    @Column(name = "date_from", nullable = false)
    private Timestamp dateFrom;

    @NotNull(message = "Start date is required")
    @Column(name = "date_to", nullable = false)
    private Timestamp dateTo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;

    public Weekend() {
    }

    public Weekend(String description, Timestamp dateFrom, Timestamp dateTo, Place place) {
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.place = place;
    }
}
