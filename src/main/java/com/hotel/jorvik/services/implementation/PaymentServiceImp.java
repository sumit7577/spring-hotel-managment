package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.payment.CreatePayment;
import com.hotel.jorvik.models.EntertainmentType;
import com.hotel.jorvik.models.Payment;
import com.hotel.jorvik.models.RoomType;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.EntertainmentTypeRepository;
import com.hotel.jorvik.repositories.PaymentRepository;
import com.hotel.jorvik.repositories.RoomTypeRepository;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.BookingService;
import com.hotel.jorvik.services.PaymentService;
import com.hotel.jorvik.util.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private final RoomTypeRepository roomTypeRepository;
    private final EntertainmentTypeRepository entertainmentTypeRepository;
    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    private final SecurityTools securityTools;

    @Override
    public int getPaymentAmount(CreatePayment createPayment) {
        User user = securityTools.retrieveUserData();
        int discount = user.getDiscount();
        if (createPayment.getPaymentType().equals("Room")) {
            Optional<RoomType> roomType = roomTypeRepository.findById(createPayment.getRoomTypeId());
            if (roomType.isEmpty()) {
                throw new IllegalArgumentException("Room type not found");
            }
            int amount = Tools.getRoomPaymentAmount(roomType.get(), createPayment.getDateFrom(), createPayment.getDateTo());
            return amount - (amount * discount / 100);
        } else {
            Optional<EntertainmentType> entertainmentType = entertainmentTypeRepository.findByName(createPayment.getPaymentType());
            if (entertainmentType.isEmpty()) {
                throw new IllegalArgumentException("Entertainment type not found");
            }
            int amount = Tools.getEntertainmentPaymentAmount(entertainmentType.get(), createPayment.getDateFrom(), createPayment.getTimeFrom(), createPayment.getDateTo(), createPayment.getTimeTo());
            return amount - (amount * discount / 100);
        }
    }

    @Override
    public int createReservation(CreatePayment createPayment) {
        if (createPayment.getPaymentType().equals("Room")) {
            return bookingService.bookRoom(createPayment.getDateFrom(), createPayment.getDateTo(), createPayment.getRoomTypeId()).getId();
        } else {
            return bookingService.bookEntertainment(createPayment.getPaymentType(), createPayment.getDateFrom(), createPayment.getTimeFrom(), createPayment.getDateTo(), createPayment.getTimeTo(), createPayment.getEntertainmentId()).getId();
        }
    }

    @Override
    public Payment createPayment(int amount) {
        Payment payment = new Payment(new Timestamp(System.currentTimeMillis()), amount);
        paymentRepository.save(payment);
        return payment;
    }
}
