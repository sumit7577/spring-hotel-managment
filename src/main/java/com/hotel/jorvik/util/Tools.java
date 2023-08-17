package com.hotel.jorvik.util;

import java.sql.Date;
import java.time.LocalDate;
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
}
