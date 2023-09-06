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
public class PasswordChangeRequest {
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Password is required")
    private String newPassword;
}
