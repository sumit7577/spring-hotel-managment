package com.hotel.jorvik.models.DTO.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetConfirmedRequest {
    @NotBlank(message = "Password is required")
    String password;
}
