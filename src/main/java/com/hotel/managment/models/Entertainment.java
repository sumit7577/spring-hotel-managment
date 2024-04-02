package com.hotel.managment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * Represents an entertainment option available, detailing its description, lock code, and type.
 * This entity is mapped to the "Entertainment" table in the database. It is linked to a specific
 * type of entertainment and can have multiple reservations.
 */
@Data
@Entity
@Table(name = "Entertainment")
public class Entertainment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @NotBlank(message = "Description is required")
  @Size(
      min = 3,
      max = 100,
      message = "Description cannot be less that 3 and more than 500 characters")
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

  public Entertainment() {}

  /**
   * Constructs a new Entertainment instance with the provided entertainment details.
   *
   * @param description The description of the entertainment option.
   * @param lockCode The lock code associated with the entertainment option.
   * @param entertainmentType The type of entertainment to which this option belongs.
   */
  public Entertainment(String description, Integer lockCode, EntertainmentType entertainmentType) {
    this.description = description;
    this.lockCode = lockCode;
    this.entertainmentType = entertainmentType;
  }
}
