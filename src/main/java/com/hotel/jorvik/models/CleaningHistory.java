package com.hotel.jorvik.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Cleaning_History")
public class CleaningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @NotNull(message = "Requested time is required")
    @Column(name = "requested_at", nullable = false)
    private Timestamp requestedAt;

    @Column(name = "done_at", nullable = false)
    @FutureOrPresent
    private Timestamp doneAt;

    public CleaningHistory() {
    }

    public CleaningHistory(Room room, Timestamp requestedAt, Timestamp doneAt) {
        this.room = room;
        this.requestedAt = requestedAt;
        this.doneAt = doneAt;
    }
}