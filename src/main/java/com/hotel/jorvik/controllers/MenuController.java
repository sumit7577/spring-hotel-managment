package com.hotel.jorvik.controllers;

import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

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
}
