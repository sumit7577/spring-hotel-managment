package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Entertainment")
public class Entertainment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entertainmentTypeID")
    private EntertainmentType entertainmentType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entertainment")
    private List<EntertainmentReservation> entertainmentReservations;

    public Entertainment() {
    }

    public Entertainment(String description, EntertainmentType entertainmentType) {
        this.description = description;
        this.entertainmentType = entertainmentType;
    }
}