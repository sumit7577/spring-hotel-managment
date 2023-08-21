package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.*;
import com.hotel.jorvik.models.DTO.bookings.AllBookingsResponse;
import com.hotel.jorvik.models.DTO.bookings.CurrentRoomResponse;
import com.hotel.jorvik.repositories.EntertainmentRepository;
import com.hotel.jorvik.repositories.EntertainmentReservationRepository;
import com.hotel.jorvik.repositories.EntertainmentTypeRepository;
import com.hotel.jorvik.repositories.RoomReservationRepository;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.BookingService;
import com.hotel.jorvik.services.RoomService;
import com.hotel.jorvik.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.hotel.jorvik.util.Tools.parseDate;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

    private final RoomService roomService;
    private final UserService userService;
    private final RoomReservationRepository roomReservationRepository;
    private final EntertainmentReservationRepository entertainmentReservationRepository;
    private final EntertainmentRepository entertainmentRepository;
    private final EntertainmentTypeRepository entertainmentTypeRepository;
    private final SecurityTools securityTools;

    @Override
    public RoomReservation bookRoom(String from, String to, int roomTypeId) {
        Date sqlFromDate = parseDate(from);
        Date sqlToDate = parseDate(to);

        List<Room> rooms = roomService.getAllByAvailableTimeAndType(from, to, roomTypeId);
        if (!rooms.iterator().hasNext()) {
            throw new IllegalArgumentException("No rooms available");
        }
        Room room = rooms.iterator().next();

        if (userService.getUserRoomReservationsCount() >= 5) {
            throw new IllegalArgumentException("You can't book more than 5 rooms");
        }
        User user = securityTools.retrieveUserData();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RoomReservation roomReservation = new RoomReservation(sqlFromDate, sqlToDate, timestamp, room, user);
        roomReservationRepository.save(roomReservation);
        return roomReservation;
    }

    @Override
    public EntertainmentReservation bookEntertainment(String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo, int entertainmentId){
        Timestamp dateTimeFrom = parseDate(dateFrom, timeFrom);
        Timestamp dateTimeTo = parseDate(dateTo, timeTo);

        User user = securityTools.retrieveUserData();

        List<EntertainmentType> entertainmentTypes = entertainmentTypeRepository.findAll();
        Optional<EntertainmentType> type = entertainmentTypes.stream()
                .filter(entertainmentType1 -> entertainmentType1
                        .getName()
                        .equals(entertainmentType))
                .findFirst();
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Entertainment type not found");
        }

        List<Entertainment> entertainments = entertainmentRepository
                .findAvailableEntertainmentsByTypeAndTime(type.get().getId(), dateTimeFrom, dateTimeTo);
        Optional<Entertainment> entertainment = entertainments.stream()
                .filter(entertainment1 -> entertainment1.getId() == entertainmentId)
                .findFirst();

        if (entertainment.isEmpty()) {
            throw new IllegalArgumentException("Entertainment not found");
        }

        if (userService.getUserEntertainmentReservationsCount() >= 10) {
            throw new IllegalArgumentException("You can't book more than 10 rooms");
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        EntertainmentReservation entertainmentReservation = new EntertainmentReservation(dateTimeFrom, dateTimeTo, timestamp, user, entertainment.get());
        entertainmentReservationRepository.save(entertainmentReservation);
        return entertainmentReservation;
    }

    @Override
    public RoomReservation getRoomReservation(int reservationId) {
        return roomReservationRepository.findById(reservationId).orElseThrow(() -> new NoSuchElementException("No reservation found"));
    }

    @Override
    public Room getLastBooking() {
        User user = securityTools.retrieveUserData();
        RoomReservation roomReservation = roomReservationRepository.findFirstByUserOrderByBookedAtDesc(user);
        if (roomReservation == null) {
            throw new NoSuchElementException("No bookings found");
        }
        return roomReservation.getRoom();
    }

    @Override
    public List<AllBookingsResponse> getAll() {
        User user = securityTools.retrieveUserData();
        List<RoomReservation> userRooms = roomReservationRepository.findAllByUser(user);
        List<EntertainmentReservation> userEntertainments = entertainmentReservationRepository.findAllByUser(user);
        return retrieveBookingsResponse(userRooms, userEntertainments);
    }

    private List<AllBookingsResponse> retrieveBookingsResponse(List<RoomReservation> userRooms, List<EntertainmentReservation> userEntertainments) {
        List<AllBookingsResponse> bookings = new ArrayList<>();
        for(RoomReservation roomReservation : userRooms) {
            bookings.add(
                    AllBookingsResponse.builder()
                            .id(roomReservation.getId())
                            .description("Hotel room reservation")
                            .price(roomReservation.getRoom().getRoomType().getPrice())
                            .name("Room")
                            .fromDate(roomReservation.getFromDate().toString())
                            .toDate(roomReservation.getToDate().toString())
                            .timestampFrom(new Timestamp(roomReservation.getFromDate().getTime()))
                            .bookingType("Room")
                            .roomTypeId(roomReservation.getRoom().getRoomType().getId())
                            .bookingStatus(getRoomBookingStatus(roomReservation))
                            .paymentId(roomReservation.getPayment() == null ? null : roomReservation.getPayment().getId())
                            .build()
                    );
        }
        for (EntertainmentReservation entertainmentReservation : userEntertainments) {
            bookings.add(
                    AllBookingsResponse.builder()
                            .id(entertainmentReservation.getId())
                            .description(entertainmentReservation.getEntertainment().getDescription())
                            .price(entertainmentReservation.getEntertainment().getEntertainmentType().getPrice())
                            .name(entertainmentReservation.getEntertainment().getEntertainmentType().getName())
                            .fromDate(entertainmentReservation.getDateFrom().toString())
                            .toDate(entertainmentReservation.getDateTo().toString())
                            .timestampFrom(entertainmentReservation.getDateFrom())
                            .bookingType(entertainmentReservation.getEntertainment().getEntertainmentType().getName())
                            .bookingStatus(getEntertainmentBookingStatus(entertainmentReservation))
                            .accessCode(entertainmentReservation.getEntertainment().getLockCode())
                            .paymentId(entertainmentReservation.getPayment() == null ? null : entertainmentReservation.getPayment().getId())
                            .build()
            );
        }

        bookings.sort((o1, o2) -> o2.getTimestampFrom().compareTo(o1.getTimestampFrom()));
        return bookings;
    }

    @Override
    public List<CurrentRoomResponse> getAllCurrentRooms() {
        User user = securityTools.retrieveUserData();
        List<RoomReservation> userRooms = roomReservationRepository.findAllByUser(user);
        List<CurrentRoomResponse> rooms = new ArrayList<>();
        for (RoomReservation roomReservation : userRooms) {
            if (getRoomBookingStatus(roomReservation) == RoomReservation.BookingStatus.ACTIVE) {
                rooms.add(
                        CurrentRoomResponse.builder()
                                .number(roomReservation.getRoom().getNumber())
                                .datePeriod(roomReservation.getFromDate().toString() + " - " + roomReservation.getToDate().toString())
                                .accessCode(roomReservation.getRoom().getAccessCode())
                                .build()
                );
            }
        }
        return rooms;
    }

    private RoomReservation.BookingStatus getRoomBookingStatus(RoomReservation roomReservation) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (roomReservation.getPayment() == null) {
            return RoomReservation.BookingStatus.AWAITING_PAYMENT;
        } else if (roomReservation.getFromDate().after(timestamp)) {
            return RoomReservation.BookingStatus.UPCOMING;
        } else if (roomReservation.getToDate().before(timestamp)) {
            return RoomReservation.BookingStatus.COMPLETED;
        } else {
            return RoomReservation.BookingStatus.ACTIVE;
        }
    }

    private RoomReservation.BookingStatus getEntertainmentBookingStatus(EntertainmentReservation entertainmentReservation) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp timestampPlusOneHour = new Timestamp(System.currentTimeMillis() + 3600000);

        if (entertainmentReservation.getDateFrom().after(timestamp)) {
            return RoomReservation.BookingStatus.UPCOMING;
        } else if (entertainmentReservation.getDateFrom().before(timestampPlusOneHour)) {
            return RoomReservation.BookingStatus.COMPLETED;
        } else {
            return RoomReservation.BookingStatus.ACTIVE;
        }
    }

    @Override
    public void addPaymentToRoomReservation(int roomReservationId, Payment payment) {
        RoomReservation roomReservation = roomReservationRepository.findById(roomReservationId).orElseThrow(() -> new NoSuchElementException("No room reservation found"));
        roomReservation.setPayment(payment);
        roomReservationRepository.save(roomReservation);
    }

    @Override
    public void deleteUnpaidRoomReservations() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<RoomReservation> roomReservations = roomReservationRepository.findAllByPaymentIsNull();
        // Delete all unpaid room reservations that are in the past or 24 hours before the reservation
        for (RoomReservation roomReservation : roomReservations) {
            if (roomReservation.getFromDate().before(timestamp) ||
                roomReservation.getFromDate().before(new Timestamp(timestamp.getTime() + 86400000))) {
                roomReservationRepository.delete(roomReservation);
            }
        }
    }

    @Override
    public void deleteRoomReservation(int reservationId) {
        User user = securityTools.retrieveUserData();
        RoomReservation reservation = roomReservationRepository.findById(reservationId).orElseThrow(() -> new NoSuchElementException("No room reservation found"));
        if (reservation.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("You can't delete this reservation");
        }
        if (reservation.getPayment() != null) {
            throw new IllegalArgumentException("You can't delete paid reservation");
        }
        roomReservationRepository.delete(reservation);
    }

    @Override
    public void deleteEntertainmentReservation(int reservationId) {
        User user = securityTools.retrieveUserData();
        EntertainmentReservation reservation = entertainmentReservationRepository.findById(reservationId).orElseThrow(() -> new NoSuchElementException("No entertainment reservation found"));
        if (reservation.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("You can't delete this reservation");
        }
        if (reservation.getPayment() != null) {
            throw new IllegalArgumentException("You can't delete paid reservation");
        }
        entertainmentReservationRepository.delete(reservation);
    }
}
