package com.hotel.managment.models.dto.auth;

import com.hotel.managment.models.Role;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an authentication response containing an authentication token and user roles. This
 * class is typically used to convey the result of a successful authentication, providing an
 * authentication token and the roles associated with the authenticated user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String token;
  private Set<Role.RoleEnum> roles;
}
