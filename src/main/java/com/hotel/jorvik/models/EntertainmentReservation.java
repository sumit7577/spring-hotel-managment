package com.hotel.jorvik.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a reservation for an entertainment activity. This entity extends the {@link
 * Reservation} class and is mapped to the "Entertainment_Reservation" table in the database. It
 * includes details about the entertainment activity, reservation dates, and associated user.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Entertainment_Reservation")
public class EntertainmentReservation extends Reservation {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "entertainment_id")
  private Entertainment entertainment;

  @Column(name = "from_date", nullable = false)
  @FutureOrPresent
  protected Timestamp dateFrom;

  @Column(name = "to_date", nullable = false)
  @FutureOrPresent
  protected Timestamp dateTo;

  public EntertainmentReservation() {}

  /**
   * Constructs a new EntertainmentReservation instance with the provided reservation details.
   *
   * @param dateFrom The starting date and time of the entertainment reservation.
   * @param dateTo The ending date and time of the entertainment reservation.
   * @param bookedAt The timestamp when the reservation was booked.
   * @param user The user associated with the reservation.
   * @param entertainment The entertainment activity for which the reservation is made.
   */
  public EntertainmentReservation(
      Timestamp dateFrom,
      Timestamp dateTo,
      Timestamp bookedAt,
      User user,
      Entertainment entertainment) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.bookedAt = bookedAt;
    this.user = user;
    this.entertainment = entertainment;
  }

  /**
   * Constructs a new EntertainmentReservation instance with the provided reservation details and
   * payment information.
   *
   * @param dateFrom The starting date and time of the entertainment reservation.
   * @param dateTo The ending date and time of the entertainment reservation.
   * @param bookedAt The timestamp when the reservation was booked.
   * @param user The user associated with the reservation.
   * @param entertainment The entertainment activity for which the reservation is made.
   * @param payment The payment information associated with the reservation.
   */
  public EntertainmentReservation(
      Timestamp dateFrom,
      Timestamp dateTo,
      Timestamp bookedAt,
      User user,
      Entertainment entertainment,
      Payment payment) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.bookedAt = bookedAt;
    this.user = user;
    this.entertainment = entertainment;
    this.payment = payment;
  }
}
