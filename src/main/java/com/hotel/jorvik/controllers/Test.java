package com.hotel.jorvik.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {

    @GetMapping
    @PreAuthorize("isAuthenticated() and principal.isEnabled()")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello World");
    }
}
