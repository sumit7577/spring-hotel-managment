package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.Role.ERole;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.implementation.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Role getRole(ERole name);
    void resetPasswordRequest(PasswordResetRequest passwordResetRequest);
    void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
