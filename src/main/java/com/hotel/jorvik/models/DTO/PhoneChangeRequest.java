package com.hotel.jorvik.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhoneChangeRequest {
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must be at most 20 characters long")
    @Pattern(regexp = "^\\+?[0-9\\s]*$", message = "Phone number must contain only digits and spaces")
    String phone;
}
