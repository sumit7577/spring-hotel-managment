package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.EntertainmentType;
import com.hotel.jorvik.models.Room;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.EntertainmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entertainment")
@RequiredArgsConstructor
public class EntertainmentController {

    private final EntertainmentService entertainmentService;

    @GetMapping("/getTypes")
    public ResponseEntity<Response> getTypes() {
        return ResponseEntity.ok().body(new SuccessResponse<>(entertainmentService.getAllEntertainmentTypes()));
    }

    @GetMapping("/getElements/{entertainmentType}/{dateFrom}/{timeFrom}/{dateTo}/{timeTo}")
    public ResponseEntity<Response> getAvailableElements(
            @PathVariable String entertainmentType,
            @PathVariable String dateFrom,
            @PathVariable String timeFrom,
            @PathVariable String dateTo,
            @PathVariable String timeTo) {
        return ResponseEntity.ok().body(new SuccessResponse<>(entertainmentService
                .getAllEntertainmentElementsByAvailableDate(entertainmentType, dateFrom, timeFrom, dateTo, timeTo)));
    }

    @PatchMapping("/update-price")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> updatePrice(@RequestBody List<EntertainmentType> entertainmentTypes) {
        entertainmentService.updatePrices(entertainmentTypes);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
