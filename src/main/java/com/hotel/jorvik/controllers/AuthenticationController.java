package com.hotel.jorvik.controllers;

import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.EmailConfirmationService;
import com.hotel.jorvik.services.interfaces.AuthenticationService;
import com.hotel.jorvik.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailConfirmationService emailConfirmation;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/email-confirmation/{token}")
    public ResponseEntity<String> confirmEmail(@PathVariable String token){
    return ResponseEntity.ok(emailConfirmation.confirmEmail(token));
    }
}
