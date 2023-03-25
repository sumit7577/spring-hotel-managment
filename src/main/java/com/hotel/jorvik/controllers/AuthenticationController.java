package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.interfaces.AuthenticationService;
import com.hotel.jorvik.security.implementation.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailService emailConfirmation;
    private final SecurityTools tools;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(new SuccessResponse<>(service.register(request)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(new SuccessResponse<>(service.authenticate(request)));
    }

    @GetMapping("/email-confirmation/{token}")
    public ResponseEntity<Response> confirmEmail(@PathVariable String token){
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Token is empty"));
        }
        emailConfirmation.confirmEmail(token);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Response> passwordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        if (service.resetPasswordRequest(passwordResetRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }

    @PostMapping("/password-reset-confirm/{token}")
    public ResponseEntity<Response> passwordResetConfirm(@PathVariable String token, @RequestBody PasswordResetConfirmedRequest passwordRequest) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Token is empty"));
        }
        if (!tools.isValidPassword(passwordRequest.getPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Password is not valid"));
        }
        if (service.resetPassword(token, passwordRequest)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }
}
