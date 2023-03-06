package com.hotel.jorvik.repositories;

import com.hotel.jorvik.models.Dish;
import jakarta.mail.Session;

import java.util.List;

public interface DishRepository {
    List<Dish> getAllDishers();
}
