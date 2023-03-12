package com.hotel.jorvik.models;

import com.hotel.jorvik.models.enums.ERole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name cannot be less that 2 and more than 50 characters")
    @Column(name = "name", nullable = false)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }
}
