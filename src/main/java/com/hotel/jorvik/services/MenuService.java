package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuItem;

import java.util.List;

public interface MenuService {
    List<Dish> getBreakfastMenu();
    List<Dish> getLunchMenu();
    List<Dish> getDinnerMenu();
}
