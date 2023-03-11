package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "LastName is required")
    @Size(min = 3, max = 50, message = "LastName cannot be less that 3 and more than 50 characters")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must be at most 20 characters long")
    @Pattern(regexp = "^\\+?[0-9\\s]*$", message = "Phone number must contain only digits and spaces")
    @Column(name = "phone")
    private String phone;


    @NotNull
    @Min(value = 0, message = "Discount must be a non-negative number")
    @Max(value = 100, message = "Discount cannot be greater than 100")
    @Column(name = "discount")
    private int discount;

    @NotBlank(message = "Hash is required")
    @Column(name = "hash")
    private String hash;

    @NotBlank(message = "Salt is required")
    @Column(name = "salt")
    private String salt;

    @Column(name = "refreshToken")
    private String refreshToken;

    @NotNull(message = "Role is required")
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
