package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.payment.CreatePayment;
import com.hotel.jorvik.models.Payment;

public interface PaymentService {
    int getPaymentAmount(CreatePayment createPayment);
    int createReservation(CreatePayment createPayment);
    Payment createPayment(int amount);
}
