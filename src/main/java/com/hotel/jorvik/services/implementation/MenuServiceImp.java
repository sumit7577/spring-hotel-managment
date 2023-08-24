package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;
import com.hotel.jorvik.repositories.DishRepository;
import com.hotel.jorvik.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {

    private final DishRepository dishRepository;

    @Override
    public List<Dish> getBreakfastMenu() {
        return dishRepository.findDishesByMenuTypeNameAndMenuDate(MenuType.EMenu.BREAKFAST, Date.valueOf(LocalDate.now()));
    }

    @Override
    public List<Dish> getLunchMenu() {
        return dishRepository.findDishesByMenuTypeNameAndMenuDate(MenuType.EMenu.LUNCH, Date.valueOf(LocalDate.now()));
    }

    @Override
    public List<Dish> getDinnerMenu() {
        return dishRepository.findDishesByMenuTypeNameAndMenuDate(MenuType.EMenu.DINNER, Date.valueOf(LocalDate.now()));
    }
}
