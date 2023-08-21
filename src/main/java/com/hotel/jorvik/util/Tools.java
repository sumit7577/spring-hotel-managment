package com.hotel.jorvik.util;

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
}
