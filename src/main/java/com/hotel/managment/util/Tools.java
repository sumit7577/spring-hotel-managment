package com.hotel.managment.util;

import com.hotel.managment.models.EntertainmentType;
import com.hotel.managment.models.RoomType;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Utility class providing methods for date parsing and payment calculations. */
public class Tools {

  /**
   * Parses a string into a SQL Date.
   *
   * @param date The date string in the format "yyyy-MM-dd".
   * @return The parsed SQL Date.
   * @throws IllegalArgumentException If the date format is not correct.
   */
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

  /**
   * Parses a date and time string into a SQL Timestamp.
   *
   * @param date The date string in the format "yyyy-MM-dd".
   * @param time The time string in the format "HH-mm".
   * @return The parsed SQL Timestamp.
   * @throws IllegalArgumentException If the date or time format is not correct.
   */
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

  /**
   * Calculates the total payment amount for a room based on the room type and duration of stay.
   *
   * @param roomType The type of the room.
   * @param dateFrom The check-in date in the format "yyyy-MM-dd".
   * @param dateTo The check-out date in the same format.
   * @return The total payment amount.
   */
  public static int getRoomPaymentAmount(RoomType roomType, String dateFrom, String dateTo) {
    int pricePerNight = roomType.getPrice();

    Date sqlFromDate = parseDate(dateFrom);
    Date sqlToDate = parseDate(dateTo);
    long nights = (sqlToDate.getTime() - sqlFromDate.getTime()) / (1000 * 60 * 60 * 24);
    return (int) (pricePerNight * nights);
  }

  /**
   * Calculates the total payment amount for an entertainment service based on the type and
   * duration.
   *
   * @param entertainmentType The type of the entertainment service.
   * @param dateFrom The start date in the format "yyyy-MM-dd".
   * @param timeFrom The start time in the format "HH-mm".
   * @param dateTo The end date in the same format.
   * @param timeTo The end time in the same format.
   * @return The total payment amount.
   */
  public static int getEntertainmentPaymentAmount(
      EntertainmentType entertainmentType,
      String dateFrom,
      String timeFrom,
      String dateTo,
      String timeTo) {
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
