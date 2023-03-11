package com.hotel.jorvik.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @Column(name = "number", nullable = false)
    @Positive
    private int number;

    @Column(name = "accessCode", nullable = false)
    @Positive
    private int accessCode;

    @Column(name = "floor", nullable = false)
    @Positive
    private int floor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomTypeID", referencedColumnName = "ID")
    private RoomType roomType;

    @Column(name = "cleaningRequest")
    private Timestamp cleaningRequest;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<RoomReservation> roomReservations;

    public Room() {
    }

    public Room(int number, int accessCode, int floor, RoomType roomType, Timestamp cleaningRequest) {
        this.number = number;
        this.accessCode = accessCode;
        this.floor = floor;
        this.roomType = roomType;
        this.cleaningRequest = cleaningRequest;
    }
}
