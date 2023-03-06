package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MenuType")
public class MenuType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "name", nullable = false)
    private String name;

    public MenuType() {
    }

    public MenuType(String name) {
        this.name = name;
    }
}
