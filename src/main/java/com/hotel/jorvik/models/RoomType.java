package com.hotel.jorvik.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Represents a type of room, specifying its occupancy, price, and area. This entity is mapped to
 * the "Room_Type" table in the database. It defines the characteristics and attributes of different
 * room types available.
 */
@Data
@Entity
@Table(name = "Room_Type")
public class RoomType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Positive
  @Column(name = "room_occupancy", nullable = false)
  private int roomOccupancy;

  @Positive
  @Column(name = "price", nullable = false)
  private int price;

  @Positive
  @Column(name = "room_area", nullable = false)
  private int roomArea;

  public RoomType() {}

  /**
   * Initializes a new instance of the RoomType class with the specified room occupancy, price, and
   * room area.
   *
   * @param roomOccupancy The maximum occupancy of the room.
   * @param price The price per night for the room.
   * @param roomArea The area of the room in square meters.
   */
  public RoomType(int roomOccupancy, int price, int roomArea) {
    this.roomOccupancy = roomOccupancy;
    this.price = price;
    this.roomArea = roomArea;
  }
}
