package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.Role.ERole;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.TokenType.ETokenType;
import com.hotel.jorvik.repositories.RoleRepository;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.*;
import com.hotel.jorvik.security.implementation.RegisterRequest;
import com.hotel.jorvik.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final SecurityTools tools;

    public AuthenticationResponse register(RegisterRequest request) {
        if (!tools.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }
        if (!tools.isValidPhone(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone is not valid");
        }
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });
        userRepository.findByPhone(request.getPhoneNumber())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Phone already exists");
                });
        Role defaultRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User type not found"));

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                0,
                passwordEncoder.encode(request.getPassword()),
                defaultRole
        );
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        emailService.sendConfirmationEmail(user);
        jwtService.saveUserToken(savedUser, jwtToken, ETokenType.ACCESS);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();
            String jwtToken = jwtService.generateToken(user);
            jwtService.revokeAllUserTokens(user, ETokenType.ACCESS);
            jwtService.saveUserToken(user, jwtToken, ETokenType.ACCESS);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    public Role getRole(ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow();
    }

    @Override
    public void resetPasswordRequest(PasswordResetRequest passwordResetRequest){
        Optional<User> user = userRepository.findByEmail(passwordResetRequest.getEmail());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        emailService.sendResetPasswordEmail(user.get());
    }

    @Override
    public void resetPassword(String token, PasswordResetConfirmedRequest passwordRequest){
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        if (!tools.isValidPassword(passwordRequest.getPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }

        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Token is invalid");
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        if (jwtService.isTokenValid(token, user) && jwtService.isPasswordToken(token)) {
            jwtService.revokeAllUserTokens(user, ETokenType.RESET_PASSWORD);
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Token is invalid");
        }
    }
}
