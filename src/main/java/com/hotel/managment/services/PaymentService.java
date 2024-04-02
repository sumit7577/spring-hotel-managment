package com.hotel.managment.services;

import com.hotel.managment.models.Payment;
import com.hotel.managment.models.dto.payment.CreatePayment;

/**
 * Interface for managing payment processing in the application.
 *
 * <p>This interface provides methods for calculating payment amounts, creating reservations, and
 * processing actual payments. It handles various aspects of payment from computation to final
 * transaction creation.
 */
public interface PaymentService {
  int getPaymentAmount(CreatePayment createPayment);

  int createReservation(CreatePayment createPayment);

  Payment createPayment(int amount);
}
