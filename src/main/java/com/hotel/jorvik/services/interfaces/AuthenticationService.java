package com.hotel.jorvik.services.interfaces;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.enums.ERole;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.implementation.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Role getRole(ERole name);
    boolean resetPasswordRequest(PasswordResetRequest passwordResetRequest);
    boolean resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
