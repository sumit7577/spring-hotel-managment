package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.EmailChangeDTO;
import com.hotel.jorvik.models.DTO.PasswordChangeDTO;
import com.hotel.jorvik.models.DTO.UserDTO;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import com.hotel.jorvik.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

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

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        if (service.updateById(id, userDTO)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("User not found"));
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Response> updatePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        if (!isValidPassword(passwordChangeDTO.getNewPassword())) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Password is not valid"));
        }
        if (service.updatePassword(passwordChangeDTO)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Password is incorrect"));
    }

    @PatchMapping("/update-email")
    public ResponseEntity<Response> updateEmail(@RequestBody EmailChangeDTO emailChangeDTO) {
        if (service.updateEmail(emailChangeDTO)) {
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

    private boolean isValidPassword(String password) {
        if(password == null || password.length() < 8) {
            return false;
        }
        if(!password.matches(".*[A-Z].*")) {
            return false;
        }
        if(!password.matches(".*[a-z].*")) {
            return false;
        }
        if(!password.matches(".*[0-9].*")) {
            return false;
        }
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*");
    }
}
