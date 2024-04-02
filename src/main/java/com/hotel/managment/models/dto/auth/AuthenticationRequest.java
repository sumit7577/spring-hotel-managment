package com.hotel.managment.models.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an authentication request containing user credentials. This class is typically used
 * for authenticating users by providing their email and password. It is commonly used in login and
 * authentication processes.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  private String email;
  private String password;
}
