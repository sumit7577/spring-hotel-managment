package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Weekend;

public interface WeekendService {
    Weekend getById(int id);
    Iterable<Weekend> getByDate(String date);
    boolean updateById(int id, Weekend weekend);
    boolean deleteById(int id);
    void create(Weekend weekend);
}

