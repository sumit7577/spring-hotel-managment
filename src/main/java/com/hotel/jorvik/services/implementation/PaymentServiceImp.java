package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    @Override
    public int getRoomPaymentAmount(int roomTypeId, String dateFrom, String dateTo) {

        return 5;
    }

    @Override
    public int getBicyclePaymentAmount(String timestampFrom) {
        return 5;
    }

    @Override
    public int getKayakPaymentAmount(String timestampFrom) {
        return 5;
    }

    @Override
    public int getTennisPaymentAmount(String timestampFrom) {
        return 5;
    }
}
