package com.hotel.managment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import lombok.Data;

/**
 * Abstract base class for various types of reservations. This class provides common fields like ID,
 * booking timestamp, associated user, and payment. It is a mapped superclass for entities
 * representing different kinds of reservations.
 */
@Data
@MappedSuperclass
public abstract class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected int id;

  @Column(name = "booked_at", nullable = false)
  protected Timestamp bookedAt;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  protected User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "payment_id")
  protected Payment payment;
}
