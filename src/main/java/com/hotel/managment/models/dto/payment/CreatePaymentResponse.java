package com.hotel.managment.models.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing details of a payment creation, including the client secret,
 * payment amount, and associated reservation ID. This class is typically used to provide
 * information about a successfully created payment transaction, including the client secret
 * required for payment processing, the payment amount, and the reservation ID associated with the
 * payment.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentResponse {
  private String clientSecret;
  private int amount;
  private int reservationId;
}
