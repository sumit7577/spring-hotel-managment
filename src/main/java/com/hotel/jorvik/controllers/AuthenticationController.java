package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.services.AuthenticationService;
import com.hotel.jorvik.security.implementation.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final EmailService emailConfirmation;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(authenticationService.register(request)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Response> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(new SuccessResponse<>(authenticationService.authenticate(request)));
    }

    @GetMapping("/email-confirmation/{token}")
    public ResponseEntity<Response> confirmEmail(@PathVariable String token){
        emailConfirmation.confirmEmail(token);
        return ResponseEntity.ok(new SuccessResponse<>(null));
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Response> passwordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        authenticationService.resetPasswordRequest(passwordResetRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PostMapping("/password-reset-confirm/{token}")
    public ResponseEntity<Response> passwordResetConfirm(@PathVariable String token, @RequestBody PasswordResetConfirmedRequest passwordRequest) {
        authenticationService.resetPassword(token, passwordRequest);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
