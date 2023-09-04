package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.restaurant.MenuForDayResponse;
import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuItem;
import com.hotel.jorvik.models.MenuType;
import com.hotel.jorvik.repositories.DishRepository;
import com.hotel.jorvik.repositories.MenuItemRepository;
import com.hotel.jorvik.repositories.MenuTypeRepository;
import com.hotel.jorvik.services.MenuService;
import com.hotel.jorvik.util.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {

    private final DishRepository dishRepository;
    private final MenuTypeRepository menuTypeRepository;
    private final MenuItemRepository menuItemRepository;

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

    @Override
    public List<MenuForDayResponse> getMenuForDay(String date) {
        Date dateSql = Tools.parseDate(date);
        return dishRepository.findDishesByMenuDate(dateSql);
    }

    @Override
    public void deleteFromDayMenu(String date, Integer id, MenuType.EMenu menuTypeName) {
        Date dateSql = Tools.parseDate(date);
        dishRepository.deleteFromDayMenu(dateSql, id, menuTypeName);
    }

    @Override
    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    @Override
    public void addDishToTheDayMenu(String date, Integer id, MenuType.EMenu menuTypeName) {
        Date dateSql = Tools.parseDate(date);
        Dish dish = dishRepository.findById(id).orElseThrow();
        MenuType menuType = menuTypeRepository.findByName(menuTypeName);
        MenuItem menuItem = new MenuItem(dateSql, dish, menuType);
        menuItemRepository.save(menuItem);
    }

    @Override
    public void addDish(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }

    @Override
    public void updateDish(Integer id, Dish dish) {
        Dish dishFromDb = dishRepository.findById(id).orElseThrow();
        dishFromDb.setName(dish.getName());
        dishFromDb.setDescription(dish.getDescription());
        dishFromDb.setPhotoDirectory(dish.getPhotoDirectory());
        dishRepository.save(dishFromDb);
    }
}
