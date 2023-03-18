package com.hotel.jorvik.models;

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
    @Column(name = "ID")
    private Integer id;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 100, message = "Description cannot be less that 3 and more than 500 characters")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Entertainment type is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entertainmentType_ID")
    private EntertainmentType entertainmentType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entertainment")
    private List<EntertainmentReservations> entertainmentReservations;

    public Entertainment() {
    }

    public Entertainment(String description, EntertainmentType entertainmentType) {
        this.description = description;
        this.entertainmentType = entertainmentType;
    }
}