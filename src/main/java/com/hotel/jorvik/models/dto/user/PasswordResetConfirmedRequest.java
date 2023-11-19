package com.hotel.jorvik.models.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to confirm a password reset, specifying the new password. This class is
 * typically used to confirm a password reset request by providing the desired new password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetConfirmedRequest {
  @NotBlank(message = "Password is required")
  private String password;
}
