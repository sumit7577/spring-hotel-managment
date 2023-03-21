package com.hotel.jorvik.controllers;

import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.EmailConfirmationService;
import com.hotel.jorvik.services.interfaces.AuthenticationService;
import com.hotel.jorvik.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final EmailConfirmationService emailConfirmation;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request){
        service.register(request);
        return ResponseEntity.ok(new SuccessResponse<>(null));
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
}
