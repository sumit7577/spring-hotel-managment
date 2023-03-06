package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "discount")
    private int discount;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

    @Column(name = "refreshToken")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<EntertainmentReservation> entertainmentReservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<RoomReservation> roomReservations;

    public User() {
    }

    public User(String firstName, String lastName, String email, String phone, int discount, String hash, String salt, String refreshToken, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.discount = discount;
        this.hash = hash;
        this.salt = salt;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
