package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Entity
@Table(name = "Entertainment")
public class Entertainment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 100, message = "Description cannot be less that 3 and more than 500 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "lock_code")
    private Integer lockCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entertainment_type_id")
    private EntertainmentType entertainmentType;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entertainment")
    private List<EntertainmentReservation> entertainmentReservations;

    public Entertainment() {
    }

    public Entertainment(String description, Integer lockCode, EntertainmentType entertainmentType) {
        this.description = description;
        this.lockCode = lockCode;
        this.entertainmentType = entertainmentType;
    }
}