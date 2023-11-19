package com.hotel.jorvik.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.jorvik.models.dto.restaurant.MenuAddRequest;
import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuItem;
import com.hotel.jorvik.models.MenuType;
import com.hotel.jorvik.repositories.DishRepository;
import com.hotel.jorvik.repositories.MenuItemRepository;
import com.hotel.jorvik.repositories.MenuTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MenuControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired MenuTypeRepository menuTypeRepository;
  @Autowired DishRepository dishRepository;
  @Autowired MenuItemRepository menuItemRepository;

  @BeforeEach
  public void setup() {
    Dish dish1 = new Dish("Dish1", "Description1", "PhotoDirectory1");
    Dish dish2 = new Dish("Dish2", "Description2", "PhotoDirectory2");
    Dish dish3 = new Dish("Dish3", "Description3", "PhotoDirectory3");
    dishRepository.saveAll(List.of(dish1, dish2, dish3));
    MenuType menuType1 = new MenuType(MenuType.MenuEnum.BREAKFAST);
    MenuType menuType2 = new MenuType(MenuType.MenuEnum.LUNCH);
    MenuType menuType3 = new MenuType(MenuType.MenuEnum.DINNER);
    menuTypeRepository.saveAll(List.of(menuType1, menuType2, menuType3));
    MenuItem menuItem1 = new MenuItem(Date.valueOf("2028-10-12"), dish1, menuType1);
    MenuItem menuItem2 = new MenuItem(Date.valueOf("2028-10-12"), dish2, menuType1);
    MenuItem menuItem3 = new MenuItem(Date.valueOf("2028-10-13"), dish3, menuType1);
    menuItemRepository.saveAll(List.of(menuItem1, menuItem2, menuItem3));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void getDishes() throws Exception {
    mockMvc
        .perform(get("/api/v1/menu/getDishes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(3)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void unauthorizedGetDishes() throws Exception {
    mockMvc.perform(get("/api/v1/menu/getDishes")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getBreakfast() throws Exception {
    mockMvc
        .perform(get("/api/v1/menu/getBreakfast"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getLunch() throws Exception {
    mockMvc
        .perform(get("/api/v1/menu/getLunch"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "USER")
  public void getDinner() throws Exception {
    mockMvc
        .perform(get("/api/v1/menu/getDinner"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(0)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void getMenuForDay() throws Exception {
    mockMvc
        .perform(get("/api/v1/menu/getMenuForDay/2028-10-12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.length()", is(2)));
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void deleteFromDayMenu() throws Exception {
    mockMvc
        .perform(delete("/api/v1/menu/deleteFromDayMenu/2028-10-12/1/BREAKFAST"))
        .andExpect(status().isOk());
    menuItemRepository
        .findAll()
        .forEach(
            menuItem -> {
              assert menuItem.getDish().getId() != 1;
            });
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void addDishToTheDayMenu() throws Exception {
    MenuAddRequest menuAddRequest =
        MenuAddRequest.builder()
            .id(1)
            .date("2028-10-12")
            .menuTypeName(MenuType.MenuEnum.BREAKFAST)
            .build();

    mockMvc
        .perform(
            post("/api/v1/menu/addDishToTheDayMenu")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(menuAddRequest)))
        .andExpect(status().isOk());
    assertEquals(4, menuItemRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void addDish() throws Exception {
    Dish dish = new Dish("Dish4", "Description4", "PhotoDirectory4");

    mockMvc
        .perform(
            post("/api/v1/menu/addDish")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dish)))
        .andExpect(status().isOk());
    assertEquals(4, dishRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void deleteDish() throws Exception {
    mockMvc.perform(delete("/api/v1/menu/deleteDish/1")).andExpect(status().isOk());
    assertEquals(2, dishRepository.findAll().size());
  }

  @Test
  @WithMockUser(username = "test@example.com", roles = "RESTAURANT")
  public void updateDish() throws Exception {
    Dish dish = new Dish("Dish4", "Description4", "PhotoDirectory4");
    mockMvc
        .perform(
            put("/api/v1/menu/updateDish/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dish)))
        .andExpect(status().isOk());
    assertEquals("Dish4", dishRepository.findById(1).get().getName());
  }
}
