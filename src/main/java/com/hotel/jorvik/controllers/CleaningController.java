package com.hotel.jorvik.controllers;


import com.hotel.jorvik.models.DTO.cleaning.CleanRoomRequest;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cleaning")
@RequiredArgsConstructor
public class CleaningController {

    private final CleaningService cleaningService;

    @GetMapping("/getRoomsToClean")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    public ResponseEntity<Response> getRoomsToClean() {
        return ResponseEntity.ok().body(new SuccessResponse<>(cleaningService.getRoomsToClean()));
    }

    @PostMapping("/cleanRoom")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLEANER')")
    public ResponseEntity<Response> cleanRoom(@RequestBody CleanRoomRequest cleanRoomRequest) {
        cleaningService.cleanRoom(cleanRoomRequest.getRoomId());
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
