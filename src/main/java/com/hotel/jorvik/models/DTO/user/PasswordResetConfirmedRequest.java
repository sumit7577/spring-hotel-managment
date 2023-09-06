package com.hotel.jorvik.models.DTO.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetConfirmedRequest {
    @NotBlank(message = "Password is required")
    String password;
}
