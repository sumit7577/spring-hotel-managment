package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.payment.CreatePayment;
import com.hotel.jorvik.models.Payment;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.repositories.PaymentRepository;
import com.hotel.jorvik.repositories.RoomTypeRepository;
import com.hotel.jorvik.services.BookingService;
import com.hotel.jorvik.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import static com.hotel.jorvik.util.Tools.parseDate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final RoomTypeRepository roomTypeRepository;
    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;

    @Override
    public int getPaymentAmount(CreatePayment createPayment) {
        return switch (createPayment.getPaymentType()) {
            case ROOM_PAYMENT ->
                    getRoomPaymentAmount(createPayment.getRoomTypeId(), createPayment.getDateFrom(), createPayment.getDateTo());
            case BICYCLE_PAYMENT ->
                    getBicyclePaymentAmount(createPayment.getTimestampFrom());
            case KAYAK_PAYMENT ->
                    getKayakPaymentAmount(createPayment.getTimestampFrom());
            case TENNIS_PAYMENT ->
                    getTennisPaymentAmount(createPayment.getTimestampFrom());
            default ->
                    throw new IllegalArgumentException("Payment type not supported");
        };
    }

    @Override
    public int createReservation(CreatePayment createPayment) {
        return switch (createPayment.getPaymentType()) {
            case ROOM_PAYMENT ->
                    bookingService.bookRoom(createPayment.getDateFrom(), createPayment.getDateTo(), createPayment.getRoomTypeId()).getId();
            case BICYCLE_PAYMENT ->
                    1;
            case KAYAK_PAYMENT ->
                    2;
            case TENNIS_PAYMENT ->
                    3;
            default ->
                    throw new IllegalArgumentException("Payment type not supported");
        };
    }

    @Override
    public Payment createPayment(int amount) {
        Payment payment = new Payment(new Timestamp(System.currentTimeMillis()), amount);
        paymentRepository.save(payment);
        return payment;
    }

    private int getRoomPaymentAmount(int roomTypeId, String dateFrom, String dateTo) {
        Optional<RoomType> roomType = roomTypeRepository.findById(roomTypeId);
        if (roomType.isEmpty()) {
            throw new IllegalArgumentException("Room type not found");
        }
        int pricePerNight = roomType.get().getPrice();

        Date sqlFromDate = parseDate(dateFrom);
        Date sqlToDate = parseDate(dateTo);

        long nights = (sqlToDate.getTime() - sqlFromDate.getTime()) / (1000 * 60 * 60 * 24) - 1;
        return (int) (pricePerNight * nights);
    }

    private int getBicyclePaymentAmount(String timestampFrom) {
        return 5;
    }

    private int getKayakPaymentAmount(String timestampFrom) {
        return 5;
    }

    private int getTennisPaymentAmount(String timestampFrom) {
        return 5;
    }
}
