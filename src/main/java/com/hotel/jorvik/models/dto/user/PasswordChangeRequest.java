package com.hotel.jorvik.models.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to change a user's password, specifying the current password and the new
 * password. This class is typically used to request a change in a user's password by providing the
 * current password and the desired new password.
 */
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
