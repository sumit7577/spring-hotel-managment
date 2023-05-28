package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeRequest {
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Password is required")
    private String newPassword;
}
