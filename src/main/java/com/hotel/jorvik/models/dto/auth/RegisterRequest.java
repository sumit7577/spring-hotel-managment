package com.hotel.jorvik.models.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user registration request, containing user information required for registration.
 * This class is typically used to gather user details such as first name, last name, phone number,
 * email, and password when a user wants to create a new account or register in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private String password;
}
