package com.hotel.managment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a reservation for a room, including booking status, reservation dates, associated
 * room, and user. This entity extends the {@link Reservation} class and is mapped to the
 * "Room_Reservation" table in the database. It tracks the booking status of the room reservation
 * and its duration.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Room_Reservation")
public class RoomReservation extends Reservation {

  /**
   * Enum representing different booking statuses for room reservations.
   *
   * <p>This enum defines the various booking statuses that can be associated with a room
   * reservation, including ACTIVE, UPCOMING, COMPLETED, and AWAITING_PAYMENT.
   */
  public enum BookingStatus {
    ACTIVE,
    UPCOMING,
    COMPLETED,
    AWAITING_PAYMENT
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

  @FutureOrPresent
  @Column(name = "from_date", nullable = false)
  private Date fromDate;

  @Future
  @Column(name = "to_date", nullable = false)
  private Date toDate;

  public RoomReservation() {}

  /**
   * Constructs a new RoomReservation instance with the provided reservation details.
   *
   * @param fromDate  The start date of the reservation.
   * @param toDate    The end date of the reservation.
   * @param bookedAt  The timestamp when the reservation was booked.
   * @param room      The room associated with the reservation.
   * @param user      The user who made the reservation.
   */
  public RoomReservation(Date fromDate, Date toDate, Timestamp bookedAt, Room room, User user) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.bookedAt = bookedAt;
    this.room = room;
    this.user = user;
  }

  /**
   * Constructs a new RoomReservation instance with the provided reservation details and payment
   * information.
   *
   * @param fromDate  The start date of the reservation.
   * @param toDate    The end date of the reservation.
   * @param bookedAt  The timestamp when the reservation was booked.
   * @param room      The room associated with the reservation.
   * @param user      The user who made the reservation.
   * @param payment   The payment information associated with the reservation.
   */
  public RoomReservation(
      Date fromDate, Date toDate, Timestamp bookedAt, Room room, User user, Payment payment) {
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.bookedAt = bookedAt;
    this.room = room;
    this.user = user;
    this.payment = payment;
  }
}
