package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.user.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.user.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.Role.ERole;
import com.hotel.jorvik.models.DTO.auth.AuthenticationRequest;
import com.hotel.jorvik.models.DTO.auth.AuthenticationResponse;
import com.hotel.jorvik.models.DTO.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Role getRole(ERole name);
    void resetPasswordRequest(PasswordResetRequest passwordResetRequest);
    void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest);
}
