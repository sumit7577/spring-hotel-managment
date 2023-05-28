package com.hotel.jorvik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Entity(name = "Place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Place name is required")
    @Size(min = 3, max = 50, message = "Place name cannot be less that 3 and more than 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    public Place() {

    }

    public Place(String name) {
        this.name = name;
    }
}
