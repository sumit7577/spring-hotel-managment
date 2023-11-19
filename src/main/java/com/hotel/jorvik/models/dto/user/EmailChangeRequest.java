package com.hotel.jorvik.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to change a user's email address, specifying the new email value. This class
 * is typically used to request a change in a user's email address by providing the desired email
 * value.
 */
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
