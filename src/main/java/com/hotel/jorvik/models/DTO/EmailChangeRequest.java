package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailChangeRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
    private String email;
}
