package com.hotel.jorvik.models.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailChangeRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
    private String email;
}
