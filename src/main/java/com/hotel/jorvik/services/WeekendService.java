package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Weekend;

public interface WeekendService {
    Weekend getById(int id);
    Iterable<Weekend> getByDate(String date);
    void updateById(int id, Weekend weekend);
    void deleteById(int id);
    Weekend create(Weekend weekend);
}

