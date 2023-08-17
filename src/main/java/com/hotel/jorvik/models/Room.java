package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number", nullable = false)
    @Positive
    private int number;

    @Column(name = "access_code", nullable = false)
    @Positive
    private int accessCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private RoomType roomType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    @JsonIgnore
    private List<RoomReservation> roomReservations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    @JsonIgnore
    private List<CleaningHistory> cleaningHistories;

    public Room() {
    }

    public Room(int number, int accessCode, RoomType roomType) {
        this.number = number;
        this.accessCode = accessCode;
        this.roomType = roomType;
    }
}