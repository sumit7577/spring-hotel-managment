package com.hotel.jorvik.models.dto.payment;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for creating a payment, specifying various payment-related details such as
 * payment type, dates, times, room type, entertainment, and reservation. This class is used to
 * convey information needed to initiate a payment transaction in the system, allowing flexibility
 * in specifying payment parameters.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayment {

  @SerializedName("paymentType")
  private String paymentType;

  @SerializedName("timeFrom")
  private String timeFrom;

  @SerializedName("timeTo")
  private String timeTo;

  @SerializedName("dateFrom")
  private String dateFrom;

  @SerializedName("dateTo")
  private String dateTo;

  @SerializedName("roomTypeId")
  private Integer roomTypeId;

  @SerializedName("entertainmentId")
  private Integer entertainmentId;

  @SerializedName("reservationId")
  private Integer reservationId;

  /**
   * Constructs a CreatePayment instance for room-related payment with the specified details.
   *
   * @param paymentType The payment type.
   * @param dateFrom The start date.
   * @param dateTo The end date.
   * @param roomTypeId The room type ID.
   */
  public CreatePayment(String paymentType, String dateFrom, String dateTo, int roomTypeId) {
    this.paymentType = paymentType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.roomTypeId = roomTypeId;
  }

  /**
   * Constructs a CreatePayment instance for room-related payment with the specified details,
   * including a reservation ID.
   *
   * @param paymentType The payment type.
   * @param dateFrom The start date.
   * @param dateTo The end date.
   * @param roomTypeId The room type ID.
   * @param reservationId The reservation ID.
   */
  public CreatePayment(
      String paymentType, String dateFrom, String dateTo, int roomTypeId, Integer reservationId) {
    this.paymentType = paymentType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.roomTypeId = roomTypeId;
    this.reservationId = reservationId;
  }

  /**
   * Constructs a CreatePayment instance for entertainment-related payment with the specified
   * details.
   *
   * @param paymentType The payment type.
   * @param dateFrom The start date.
   * @param dateTo The end date.
   * @param timeFrom The start time.
   * @param timeTo The end time.
   * @param entertainmentId The entertainment ID.
   */
  public CreatePayment(
      String paymentType,
      String dateFrom,
      String dateTo,
      String timeFrom,
      String timeTo,
      Integer entertainmentId) {
    this.paymentType = paymentType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.entertainmentId = entertainmentId;
  }

  /**
   * Constructs a CreatePayment instance for entertainment-related payment with the specified
   * details, including a reservation ID.
   *
   * @param paymentType The payment type.
   * @param dateFrom The start date.
   * @param dateTo The end date.
   * @param timeFrom The start time.
   * @param timeTo The end time.
   * @param entertainmentId The entertainment ID.
   * @param reservationId The reservation ID.
   */
  public CreatePayment(
      String paymentType,
      String dateFrom,
      String dateTo,
      String timeFrom,
      String timeTo,
      Integer entertainmentId,
      Integer reservationId) {
    this.paymentType = paymentType;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.entertainmentId = entertainmentId;
    this.reservationId = reservationId;
  }
}
