package com.hotel.jorvik.util;

import com.hotel.jorvik.models.EntertainmentType;
import com.hotel.jorvik.models.RoomType;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Tools {
    public static Date parseDate(String date) {
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

    public static Timestamp parseDate(String date, String time) {
        String combinedDate = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        Timestamp dateTime;
        try {
            dateTime = Timestamp.valueOf(LocalDateTime.parse(combinedDate, formatter));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return dateTime;
    }

    public static int getRoomPaymentAmount(RoomType roomType, String dateFrom, String dateTo) {
        int pricePerNight = roomType.getPrice();

        Date sqlFromDate = parseDate(dateFrom);
        Date sqlToDate = parseDate(dateTo);
        long nights = (sqlToDate.getTime() - sqlFromDate.getTime()) / (1000 * 60 * 60 * 24);
        return (int) (pricePerNight * nights);
    }

    public static int getEntertainmentPaymentAmount(EntertainmentType entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo) {
        int pricePerHour = entertainmentType.getPrice();

        Timestamp sqlFromTimestamp = parseDate(dateFrom.split("\\s")[0], timeFrom);
        Timestamp sqlToTimestamp = parseDate(dateTo.split("\\s")[0], timeTo);

        long differenceInMilliseconds = sqlToTimestamp.getTime() - sqlFromTimestamp.getTime();

        // Calculate hours and minutes separately
        long totalHours = differenceInMilliseconds / (1000 * 60 * 60);
        long minutes = (differenceInMilliseconds % (1000 * 60 * 60)) / (1000 * 60);

        // If there are any minutes, round up the hour
        if (minutes > 0) {
            totalHours++;
        }

        return (int) (pricePerHour * totalHours);
    }
}
