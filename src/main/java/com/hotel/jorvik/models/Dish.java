package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 500, message = "Description cannot be less that 3 and more than 500 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "Photo is required")
    @Column(name = "photo_directory")
    private String photoDirectory;

    public Dish() {
    }

    public Dish(String name, String description, String photoDirectory) {
        this.name = name;
        this.description = description;
        this.photoDirectory = photoDirectory;
    }
}
