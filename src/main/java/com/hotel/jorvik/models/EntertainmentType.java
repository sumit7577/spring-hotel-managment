package com.hotel.jorvik.models;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "EntertainmentType")
public class EntertainmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    public EntertainmentType() {
    }

    public EntertainmentType(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
