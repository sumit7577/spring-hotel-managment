package com.hotel.jorvik.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to reset a user's password, specifying the user's email address. This class
 * is typically used to request a password reset by providing the user's email address.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be a valid email address")
  @Size(min = 3, max = 100, message = "Email must be 3-100 characters long")
  private String email;
}
