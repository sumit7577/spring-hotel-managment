package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.services.UserService;
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
    public ResponseEntity<Response> getByToken() {
        UserDTO user = userService.getByToken();
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(user));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok().body(new SuccessResponse<>(userService.getAll()));
    }

    @GetMapping("/check-email-verification")
    public ResponseEntity<Response> checkEmailVerification() {
        boolean isVerified = userService.checkEmailVerification();
        return ResponseEntity.ok().body(new SuccessResponse<>(isVerified));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Response> updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        userService.updatePassword(passwordChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-email")
    public ResponseEntity<Response> updateEmail(@RequestBody EmailChangeRequest emailChangeRequest) {
        return ResponseEntity.ok().body(new SuccessResponse<>(userService.updateEmail(emailChangeRequest)));
    }

    @GetMapping("/resend-email")
    public ResponseEntity<Response> resendEmailVerification() {
        userService.resentEmailVerification();
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PutMapping("/update-phone")
    public ResponseEntity<Response> updatePhone(@RequestBody PhoneChangeRequest phoneChangeRequest) {
        userService.updatePhone(phoneChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-discount/{id}")
    public ResponseEntity<Response> updateDiscount(@PathVariable Integer id, @RequestBody DiscountChangeRequest discountChangeRequest) {
        userService.updateDiscount(id, discountChangeRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
