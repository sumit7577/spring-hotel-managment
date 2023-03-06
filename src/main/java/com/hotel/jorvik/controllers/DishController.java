package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping("/dishes")
    public List<Dish> showAllDishes(){
        List<Dish> allDishes = dishService.getAllDishes();
        return allDishes;
    }
}
