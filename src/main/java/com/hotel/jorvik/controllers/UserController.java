package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.user.*;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> getByToken() {
        UserDTO user = userService.getByToken();
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get-by-matching/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Response> getByMatching(@PathVariable String name) {
        return ResponseEntity.ok().body(new SuccessResponse<>(userService.getByMatching(name)));
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok().body(new SuccessResponse<>(userService.getAll()));
    }

    @GetMapping("/check-email-verification")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> checkEmailVerification() {
        boolean isVerified = userService.checkEmailVerification();
        return ResponseEntity.ok().body(new SuccessResponse<>(isVerified));
    }

    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> updatePassword(@RequestBody @Valid PasswordChangeRequest passwordChangeRequest){
        userService.updatePassword(passwordChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-email")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> updateEmail(@RequestBody @Valid EmailChangeRequest emailChangeRequest) {
        return ResponseEntity.ok().body(new SuccessResponse<>(userService.updateEmail(emailChangeRequest)));
    }

    @GetMapping("/resend-email")
    public ResponseEntity<Response> resendEmailVerification() {
        userService.resentEmailVerification();
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-phone")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> updatePhone(@RequestBody @Valid PhoneChangeRequest phoneChangeRequest) {
        userService.updatePhone(phoneChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @GetMapping("/get-user-room-reservations-count")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> getUserRoomReservationsCount() {
        int count = userService.getUserRoomReservationsCount();
        return ResponseEntity.ok().body(new SuccessResponse<>(count));
    }

    @GetMapping("/get-user-entertainment-reservations-count")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
    public ResponseEntity<Response> getUserEntertainmentReservationsCount() {
        int count = userService.getUserEntertainmentReservationsCount();
        return ResponseEntity.ok().body(new SuccessResponse<>(count));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-discount/{id}")
    public ResponseEntity<Response> updateDiscount(@PathVariable Integer id, @RequestBody @Valid DiscountChangeRequest discountChangeRequest) {
        userService.updateDiscount(id, discountChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
