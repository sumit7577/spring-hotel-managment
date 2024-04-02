package com.hotel.managment.services;

import com.hotel.managment.models.dto.auth.AuthenticationRequest;
import com.hotel.managment.models.dto.auth.AuthenticationResponse;
import com.hotel.managment.models.dto.auth.RegisterRequest;
import com.hotel.managment.models.dto.user.PasswordResetConfirmedRequest;
import com.hotel.managment.models.dto.user.PasswordResetRequest;

/**
 * Interface for authentication services in the application.
 *
 * <p>This interface defines methods for user registration, authentication, and password reset
 * functionalities.
 */
public interface AuthenticationService {
  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);

  void resetPasswordRequest(PasswordResetRequest passwordResetRequest);

  void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
