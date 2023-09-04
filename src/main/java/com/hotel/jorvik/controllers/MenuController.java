package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.restaurant.MenuAddRequest;
import com.hotel.jorvik.models.Dish;
import com.hotel.jorvik.models.MenuType;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/deleteFromDayMenu/{date}/{id}/{menuTypeName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> deleteFromDayMenu(@PathVariable String date, @PathVariable Integer id, @PathVariable MenuType.EMenu menuTypeName) {
        menuService.deleteFromDayMenu(date, id, menuTypeName);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PostMapping("/addDishToTheDayMenu")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> addDishToTheDayMenu(@RequestBody MenuAddRequest menuAddRequest) {
        menuService.addDishToTheDayMenu(menuAddRequest.getDate(), menuAddRequest.getId(), menuAddRequest.getMenuTypeName());
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
