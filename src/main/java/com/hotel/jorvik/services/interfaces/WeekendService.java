package com.hotel.jorvik.services.interfaces;

import com.hotel.jorvik.models.Weekend;
import java.util.Date;

public interface WeekendService {
    Weekend getWeekendById(int id);
    Iterable<Weekend> getWeekendsByDate(Date date);
    boolean updateWeekendById(int id, Weekend weekend);
    boolean deleteWeekendById(int id);
    void createWeekend(Weekend weekend);
}
