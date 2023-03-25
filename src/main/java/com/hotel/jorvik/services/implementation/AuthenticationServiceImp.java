package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.PasswordResetConfirmedRequest;
import com.hotel.jorvik.models.DTO.PasswordResetRequest;
import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ERole;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.RoleRepository;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.*;
import com.hotel.jorvik.security.implementation.EmailSender;
import com.hotel.jorvik.security.implementation.RegisterRequest;
import com.hotel.jorvik.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailSender emailSender;
    private final EmailService emailService;
    @Value("${site.domain}")
    private String domain;

    public AuthenticationResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });
        userRepository.findByPhone(request.getPhoneNumber())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Phone already exists");
                });
        Role defaultRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow();

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
        String confirmationToken = jwtService.generateConfirmationToken(user);
        String confirmEmailLink = domain + "/api/auth/email-confirmation/" + confirmationToken;
        emailSender.sendEmail(
                request.getEmail(),
                "Confirm your email",
                "Please, confirm your email by clicking on the link: " + confirmEmailLink);
        jwtService.saveUserToken(savedUser, jwtToken, ETokenType.BEARER);
        jwtService.saveUserToken(savedUser, confirmationToken, ETokenType.CONFIRMATION);
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
            jwtService.revokeAllUserTokens(user, ETokenType.BEARER);
            jwtService.saveUserToken(user, jwtToken, ETokenType.BEARER);
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
    public boolean resetPasswordRequest(PasswordResetRequest passwordResetRequest){
        Optional<User> user = userRepository.findByEmail(passwordResetRequest.getEmail());
        if (user.isEmpty()) {
            return false;
        }
        emailService.sendResetPasswordEmail(user.get());
        return true;
    }

    @Override
    public boolean resetPassword(String token, PasswordResetConfirmedRequest passwordRequest){
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail == null) {
            return false;
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        if (jwtService.isTokenValid(token, user) && jwtService.isPasswordToken(token)) {
            jwtService.revokeAllUserTokens(user, ETokenType.RESET_PASSWORD);
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            userRepository.save(user);
        }
        return true;
    }
}
