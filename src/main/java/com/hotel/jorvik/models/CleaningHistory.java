package com.hotel.jorvik.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;

/**
 * Represents the cleaning history of a room. This entity is mapped to the "Cleaning_History" table
 * in the database.
 */
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

  public CleaningHistory() {}

  public CleaningHistory(Room room) {
    this.room = room;
  }

  public CleaningHistory(Room room, Timestamp cleanedAt) {
    this.room = room;
    this.cleanedAt = cleanedAt;
  }
}
