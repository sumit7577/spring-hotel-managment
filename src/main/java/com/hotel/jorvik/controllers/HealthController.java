package com.hotel.jorvik.controllers;

import com.hotel.jorvik.responses.Response;
import com.hotel.jorvik.responses.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the AWS health check controller for monitoring the health and availability
 * of the application. It provides an endpoint specifically designed for AWS Elastic Load Balancer
 * (ELB) health checks. AWS ELB performs periodic health checks on this endpoint to determine the
 * health status of the application.
 */
@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

  @GetMapping("/test")
  public ResponseEntity<Response> getDishes() {
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
