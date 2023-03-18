package com.hotel.jorvik.models;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Entertainment_Type")
public class EntertainmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive
    @Column(name = "price", nullable = false)
    private Integer price;

    public EntertainmentType() {
    }

    public EntertainmentType(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
