package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * Represents a type of entertainment, including its name and price. This entity is mapped to the
 * "Entertainment_Type" table in the database. It serves as a category for different entertainment
 * options available.
 */
@Data
@Entity
@Table(name = "Entertainment_Type")
public class EntertainmentType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @NotBlank
  @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
  @Column(name = "name", nullable = false)
  private String name;

  @Positive
  @Column(name = "price", nullable = false)
  private int price;

  public EntertainmentType() {}

  public EntertainmentType(String name, Integer price) {
    this.name = name;
    this.price = price;
  }
}
