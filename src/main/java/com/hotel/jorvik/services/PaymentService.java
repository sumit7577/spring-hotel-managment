package com.hotel.jorvik.services;

public interface PaymentService {
    int getRoomPaymentAmount(int roomTypeId, String dateFrom, String dateTo);
    int getBicyclePaymentAmount(String timestampFrom);
    int getKayakPaymentAmount(String timestampFrom);
    int getTennisPaymentAmount(String timestampFrom);
}
