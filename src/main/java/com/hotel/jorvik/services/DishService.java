package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishService implements DishServiceImpl{

    @Autowired
    private DishRepository dishRepository;

    @Override
    @Transactional
    public List<Dish> getAllDishes() {
        return dishRepository.getAllDishers();
    }
}
