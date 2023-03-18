package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Role;
import com.hotel.jorvik.models.Token;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ERole;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.RoleRepository;
import com.hotel.jorvik.repositories.TokenRepository;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.AuthenticationRequest;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.JwtService;
import com.hotel.jorvik.security.RegisterRequest;
import com.hotel.jorvik.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

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
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Role getRole(ERole name) {
        return roleRepository.findByName(name)
                .orElseThrow();
    }

    private void revokeAllUserTokens(User user){
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(ETokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
