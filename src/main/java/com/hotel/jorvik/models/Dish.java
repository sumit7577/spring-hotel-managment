package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "photoDirectory")
    private String photoDirectory;

    public Dish() {
    }

    public Dish(String name, String description, String photoDirectory) {
        this.name = name;
        this.description = description;
        this.photoDirectory = photoDirectory;
    }
}
