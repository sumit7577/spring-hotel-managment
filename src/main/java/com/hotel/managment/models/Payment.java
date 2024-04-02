package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import java.sql.Timestamp;
import lombok.Data;

/**
 * Represents a payment transaction, including its date and amount. This entity is mapped to the
 * "Payment" table in the database. It records the essential details of financial transactions.
 */
@Data
@Entity
@Table(name = "Payment")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "date", nullable = false)
  private Timestamp date;

  @Positive
  @Column(name = "amount", nullable = false)
  private int amount;

  public Payment() {}

  public Payment(Timestamp date, int amount) {
    this.date = date;
    this.amount = amount;
  }
}
