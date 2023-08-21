package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Entertainment;
import com.hotel.jorvik.models.EntertainmentType;

import java.util.List;

public interface EntertainmentService {
    List<EntertainmentType> getAllEntertainmentTypes();
    List<Entertainment> getAllEntertainmentElementsByAvailableDate(String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo);
}
