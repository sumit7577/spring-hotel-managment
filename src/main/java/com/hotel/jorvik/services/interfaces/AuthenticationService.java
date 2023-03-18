package com.hotel.jorvik.services.interfaces;

import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.enums.ERole;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Role getRole(ERole name);
}
