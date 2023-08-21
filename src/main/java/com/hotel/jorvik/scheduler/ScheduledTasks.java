package com.hotel.jorvik.scheduler;

import com.hotel.jorvik.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final BookingService bookingService;

    @Scheduled(fixedRate = 30 * 60 * 1000) // every 30 minutes
    public void cleanUpExpiredReservations() {
        log.info("Cleaning up expired reservations");
        bookingService.deleteUnpaidRoomReservations();
        bookingService.deleteUnpaidEntertainmentReservations();
    }
}