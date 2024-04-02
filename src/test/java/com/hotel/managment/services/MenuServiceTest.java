package com.hotel.managment.services;

import com.hotel.managment.models.dto.restaurant.MenuForDayResponse;
import com.hotel.managment.models.Dish;
import com.hotel.managment.models.MenuType;
import com.hotel.managment.repositories.DishRepository;
import com.hotel.managment.repositories.MenuItemRepository;
import com.hotel.managment.repositories.MenuTypeRepository;
import com.hotel.managment.services.implementation.MenuServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

  @InjectMocks private MenuServiceImp menuService;

  @Mock private DishRepository dishRepository;

  @Mock private MenuTypeRepository menuTypeRepository;

  @Mock private MenuItemRepository menuItemRepository;

  private final Dish dish1 = new Dish("Pancake", "Delicious pancake", "dir1");
  private final Dish dish2 = new Dish("Salad", "Healthy salad", "dir2");

  @Test
  void testGetBreakfastMenu() {
    when(dishRepository.findDishesByMenuTypeNameAndMenuDate(any(), any()))
        .thenReturn(Arrays.asList(dish1, dish2));
    List<Dish> result = menuService.getBreakfastMenu();
    assertEquals(2, result.size());
  }

  @Test
  void testGetLunchMenu() {
    when(dishRepository.findDishesByMenuTypeNameAndMenuDate(any(), any()))
        .thenReturn(List.of(dish1));
    List<Dish> result = menuService.getLunchMenu();
    assertEquals(1, result.size());
  }

  @Test
  void testAddDishToTheDayMenu() {
    MenuType menuType = new MenuType(MenuType.MenuEnum.BREAKFAST);
    when(dishRepository.findById(anyInt())).thenReturn(Optional.of(dish1));
    when(menuTypeRepository.findByName(any())).thenReturn(menuType);

    menuService.addDishToTheDayMenu("2023-01-01", 1, MenuType.MenuEnum.BREAKFAST);

    verify(menuItemRepository, times(1)).save(any());
  }

  @Test
  void testGetMenuForDay() {
    MenuForDayResponse menuForDayResponse1 =
        new MenuForDayResponse(1, "Pancake", MenuType.MenuEnum.LUNCH);
    MenuForDayResponse menuForDayResponse2 =
        new MenuForDayResponse(2, "Fish", MenuType.MenuEnum.LUNCH);
    when(dishRepository.findDishesByMenuDate(any()))
        .thenReturn(Arrays.asList(menuForDayResponse1, menuForDayResponse2));
    List<MenuForDayResponse> result = menuService.getMenuForDay("2023-01-01");
    assertEquals(2, result.size());
  }

  @Test
  void testDeleteFromDayMenu() {
    doNothing().when(dishRepository).deleteFromDayMenu(any(), anyInt(), any());
    menuService.deleteFromDayMenu("2023-01-01", 1, MenuType.MenuEnum.BREAKFAST);
    verify(dishRepository, times(1)).deleteFromDayMenu(any(), anyInt(), any());
  }

  @Test
  void testGetDishes() {
    when(dishRepository.findAll()).thenReturn(Arrays.asList(dish1, dish2));
    List<Dish> result = menuService.getDishes();
    assertEquals(2, result.size());
  }

  @Test
  void testAddDish() {
    when(dishRepository.save(any())).thenReturn(dish1);
    menuService.addDish(dish1);
    verify(dishRepository, times(1)).save(dish1);
  }

  @Test
  void testDeleteDish() {
    doNothing().when(dishRepository).deleteById(anyInt());
    menuService.deleteDish(1);
    verify(dishRepository, times(1)).deleteById(1);
  }

  @Test
  void testUpdateDish() {
    when(dishRepository.findById(anyInt())).thenReturn(Optional.of(dish1));
    Dish newDish = new Dish("New Pancake", "New description", "dir1");

    menuService.updateDish(1, newDish);

    verify(dishRepository, times(1))
        .save(
            argThat(
                dish ->
                    dish.getName().equals("New Pancake")
                        && dish.getDescription().equals("New description")));
  }
}
