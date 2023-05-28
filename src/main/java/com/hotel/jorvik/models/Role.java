package com.hotel.jorvik.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {

    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_CLEANER,
        ROLE_RESTAURANT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}