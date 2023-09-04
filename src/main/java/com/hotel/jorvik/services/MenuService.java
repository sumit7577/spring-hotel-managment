package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.restaurant.MenuForDayResponse;
import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;

import java.util.List;

public interface MenuService {
    List<Dish> getBreakfastMenu();
    List<Dish> getLunchMenu();
    List<Dish> getDinnerMenu();
    List<MenuForDayResponse> getMenuForDay(String date);
    void deleteFromDayMenu(String date, Integer id, MenuType.EMenu menuTypeName);
    List<Dish> getDishes();
    void addDishToTheDayMenu(String date, Integer id, MenuType.EMenu menuTypeName);
    void addDish(Dish dish);
    void deleteDish(Integer id);
    void updateDish(Integer id, Dish dish);
}
