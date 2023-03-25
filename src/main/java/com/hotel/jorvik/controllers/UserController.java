package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final SecurityTools tools;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        UserDTO user = service.getById(id);
        if (user == null) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok().body(new SuccessResponse<>(service.getAll()));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Response> updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        if (!tools.isValidPassword(passwordChangeRequest.getNewPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Password is not valid"));
        }
        if (service.updatePassword(passwordChangeRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Password is incorrect"));
    }

    @PutMapping("/update-email")
    public ResponseEntity<Response> updateEmail(@RequestBody EmailChangeRequest emailChangeRequest) {
        if (service.updateEmail(emailChangeRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }

    @GetMapping("/resend-email")
    public ResponseEntity<Response> resendEmailVerification() {
        if (service.resentEmailVerification()) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }

    @PutMapping("/update-phone")
    public ResponseEntity<Response> updatePhone(@RequestBody PhoneChangeRequest phoneChangeRequest) {
        if (!tools.isValidPhone(phoneChangeRequest.getPhone())) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Phone is not valid"));
        }
        if (service.updatePhone(phoneChangeRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-discount/{id}")
    public ResponseEntity<Response> updateDiscount(@PathVariable Integer id, @RequestBody DiscountChangeRequest discountChangeRequest) {
        if (service.updateDiscount(id, discountChangeRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }
}
