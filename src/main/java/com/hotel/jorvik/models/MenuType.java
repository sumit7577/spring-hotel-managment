package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Menu_Type")
public class MenuType {

    public enum EMenu {
        BREAKFAST,
        LUNCH,
        DINNER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private EMenu name;

    public MenuType() {
    }

    public MenuType(EMenu name) {
        this.name = name;
    }
}
