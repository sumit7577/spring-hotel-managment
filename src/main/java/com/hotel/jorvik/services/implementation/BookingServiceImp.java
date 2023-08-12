package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.bookings.AllBookingsResponse;
import com.hotel.jorvik.models.DTO.bookings.CurrentRoomResponse;
import com.hotel.jorvik.models.EntertainmentReservation;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.models.RoomReservation;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.EntertainmentReservationRepository;
import com.hotel.jorvik.repositories.RoomReservationRepository;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.BookingService;
import com.hotel.jorvik.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {

    private final RoomService roomService;
    private final RoomReservationRepository roomReservationRepository;
    private final EntertainmentReservationRepository entertainmentReservationRepository;
    private final SecurityTools securityTools;

    @Override
    public Room bookRoom(String from, String to, int roomTypeId) {
        Date sqlFromDate = parseDate(from);
        Date sqlToDate = parseDate(to);

        List<Room> rooms = roomService.getAllByAvailableTimeAndType(from, to, roomTypeId);
        if (!rooms.iterator().hasNext()) {
            throw new IllegalArgumentException("No rooms available");
        }
        Room room = rooms.iterator().next();

        User user = securityTools.retrieveUserData();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RoomReservation roomReservation = new RoomReservation(sqlFromDate, sqlToDate, timestamp, room, user);
        roomReservationRepository.save(roomReservation);
        return room;
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
                            .description("Hotel room number " + roomReservation.getRoom().getNumber())
                            .price(roomReservation.getRoom().getRoomType().getPrice())
                            .name("Room")
                            .datePeriod(roomReservation.getFromDate().toString() + " - " + roomReservation.getToDate().toString())
                            .dateFrom(new Timestamp(roomReservation.getFromDate().getTime()))
                            .bookingType("Room")
                            .bookingStatus(getRoomBookingStatus(roomReservation))
                            .accessCode(roomReservation.getRoom().getAccessCode())
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
                            .datePeriod(entertainmentReservation.getDate().toString())
                            .dateFrom(entertainmentReservation.getDate())
                            .bookingType(entertainmentReservation.getEntertainment().getEntertainmentType().getName())
                            .bookingStatus(getEntertainmentBookingStatus(entertainmentReservation))
                            .accessCode(entertainmentReservation.getEntertainment().getLockCode())
                            .build()
            );
        }

        bookings.sort((o1, o2) -> o2.getDateFrom().compareTo(o1.getDateFrom()));
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

    private Date parseDate(String date) {
        Date sqlDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            sqlDate = Date.valueOf(parsedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return sqlDate;
    }

    private RoomReservation.BookingStatus getRoomBookingStatus(RoomReservation roomReservation) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (roomReservation.getFromDate().after(timestamp)) {
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

        if (entertainmentReservation.getDate().after(timestamp)) {
            return RoomReservation.BookingStatus.UPCOMING;
        } else if (entertainmentReservation.getDate().before(timestampPlusOneHour)) {
            return RoomReservation.BookingStatus.COMPLETED;
        } else {
            return RoomReservation.BookingStatus.ACTIVE;
        }
    }
}
