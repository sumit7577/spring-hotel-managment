package com.hotel.jorvik.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to update user information, specifying the fields that can be modified. This
 * class is typically used to request updates to user details by specifying the new values for the
 * fields that can be modified.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private int discount;
}
