package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;
import com.hotel.jorvik.models.dto.restaurant.MenuAddRequest;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the controller for managing menu-related operations in the API. It provides
 * endpoints for retrieving dishes, breakfast, lunch, dinner menus, menu for a specific day, adding
 * and deleting dishes from the day's menu, adding, deleting, and updating individual dishes, and
 * managing access control through role-based authorization.
 */
@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  @GetMapping("/getDishes")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> getDishes() {
    return ResponseEntity.ok().body(new SuccessResponse<>(menuService.getDishes()));
  }

  @GetMapping("/getBreakfast")
  public ResponseEntity<Response> getBreakfast() {
    return ResponseEntity.ok().body(new SuccessResponse<>(menuService.getBreakfastMenu()));
  }

  @GetMapping("/getLunch")
  public ResponseEntity<Response> getLunch() {
    return ResponseEntity.ok().body(new SuccessResponse<>(menuService.getLunchMenu()));
  }

  @GetMapping("/getDinner")
  public ResponseEntity<Response> getDinner() {
    return ResponseEntity.ok().body(new SuccessResponse<>(menuService.getDinnerMenu()));
  }

  @GetMapping("/getMenuForDay/{date}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> getMenuForDay(@PathVariable String date) {
    return ResponseEntity.ok().body(new SuccessResponse<>(menuService.getMenuForDay(date)));
  }

  /**
   * Deletes a dish from the menu for a specific day.
   *
   * @param date The date for which the dish should be deleted from the menu.
   * @param id The ID of the dish to be deleted.
   * @param menuTypeName The type of menu (e.g., breakfast, lunch, dinner) from which the dish
   *     should be deleted.
   * @return A ResponseEntity with a SuccessResponse indicating the operation was successful.
   */
  @DeleteMapping("/deleteFromDayMenu/{date}/{id}/{menuTypeName}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> deleteFromDayMenu(
      @PathVariable String date,
      @PathVariable Integer id,
      @PathVariable MenuType.MenuEnum menuTypeName) {
    menuService.deleteFromDayMenu(date, id, menuTypeName);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  /**
   * Adds a dish to the menu for a specific day.
   *
   * @param menuAddRequest The request containing the date, ID of the dish to be added, and the type
   *     of menu (e.g., breakfast, lunch, dinner) to which the dish should be added.
   * @return A ResponseEntity with a SuccessResponse indicating the operation was successful.
   */
  @PostMapping("/addDishToTheDayMenu")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> addDishToTheDayMenu(@RequestBody MenuAddRequest menuAddRequest) {
    menuService.addDishToTheDayMenu(
        menuAddRequest.getDate(), menuAddRequest.getId(), menuAddRequest.getMenuTypeName());
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PostMapping("/addDish")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> addDish(@RequestBody Dish dish) {
    menuService.addDish(dish);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @DeleteMapping("/deleteDish/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> deleteDish(@PathVariable Integer id) {
    menuService.deleteDish(id);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }

  @PutMapping("/updateDish/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
  public ResponseEntity<Response> updateDish(@PathVariable Integer id, @RequestBody Dish dish) {
    menuService.updateDish(id, dish);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
