package com.hotel.managment.services;

import com.hotel.managment.models.Dish;
import com.hotel.managment.models.MenuType;
import com.hotel.managment.models.dto.restaurant.MenuForDayResponse;
import java.util.List;

/**
 * Interface for managing the menu services in the application.
 *
 * <p>This interface provides methods for retrieving and managing different meal menus (breakfast,
 * lunch, dinner) and specific menus for particular days. It includes functionalities for adding,
 * updating, and deleting dishes from the menu.
 */
public interface MenuService {
  List<Dish> getBreakfastMenu();

  List<Dish> getLunchMenu();

  List<Dish> getDinnerMenu();

  List<MenuForDayResponse> getMenuForDay(String date);

  void deleteFromDayMenu(String date, Integer id, MenuType.MenuEnum menuTypeName);

  List<Dish> getDishes();

  void addDishToTheDayMenu(String date, Integer id, MenuType.MenuEnum menuTypeName);

  void addDish(Dish dish);

  void deleteDish(Integer id);

  void updateDish(Integer id, Dish dish);
}
