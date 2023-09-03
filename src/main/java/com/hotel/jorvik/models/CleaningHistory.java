package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @Column(name = "cleaned_at", nullable = false)
    private Timestamp cleanedAt;

    public CleaningHistory() {
    }

    public CleaningHistory(Room room) {
        this.room = room;
    }

    public CleaningHistory(Room room, Timestamp cleanedAt) {
        this.room = room;
        this.cleanedAt = cleanedAt;
    }
}
