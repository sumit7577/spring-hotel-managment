package com.hotel.managment.models.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to change a user's phone number, specifying the new phone number value. This
 * class is typically used to request a change in a user's phone number by providing the desired
 * phone number value.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneChangeRequest {
  @NotBlank(message = "Phone number is required")
  @Size(max = 20, message = "Phone number must be at most 20 characters long")
  @Pattern(regexp = "^\\+?[0-9\\s]*$", message = "Phone number must contain only digits and spaces")
  private String phone;
}
