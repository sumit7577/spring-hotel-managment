package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        UserDTO user = service.getById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok().body(new SuccessResponse<>(service.getAll()));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Response> updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        service.updatePassword(passwordChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-email")
    public ResponseEntity<Response> updateEmail(@RequestBody EmailChangeRequest emailChangeRequest) {
        service.updateEmail(emailChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @GetMapping("/resend-email")
    public ResponseEntity<Response> resendEmailVerification() {
        service.resentEmailVerification();
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-phone")
    public ResponseEntity<Response> updatePhone(@RequestBody PhoneChangeRequest phoneChangeRequest) {
        service.updatePhone(phoneChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-discount/{id}")
    public ResponseEntity<Response> updateDiscount(@PathVariable Integer id, @RequestBody DiscountChangeRequest discountChangeRequest) {
        service.updateDiscount(id, discountChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
