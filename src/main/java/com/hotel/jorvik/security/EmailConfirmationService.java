package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConfirmationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String confirmEmail(String token) {
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(token, user)) {
                jwtService.revokeAllUserTokens(user, ETokenType.CONFIRMATION);
                user.setEnabled(true);
                userRepository.save(user);
                return "ok";
            }
        }
        return "not ok";
    }
}
