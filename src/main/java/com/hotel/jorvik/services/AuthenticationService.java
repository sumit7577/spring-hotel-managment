package com.hotel.jorvik.services;

import com.hotel.jorvik.models.dto.auth.AuthenticationRequest;
import com.hotel.jorvik.models.dto.auth.AuthenticationResponse;
import com.hotel.jorvik.models.dto.auth.RegisterRequest;
import com.hotel.jorvik.models.dto.user.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.dto.user.PasswordResetRequest;

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
