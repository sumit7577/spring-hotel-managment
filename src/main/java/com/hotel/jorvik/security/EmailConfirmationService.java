package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void confirmEmail(String token) {
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(token, user) && jwtService.isEmailToken(token)) {
                jwtService.revokeAllUserTokens(user, ETokenType.CONFIRMATION);
                user.setEnabled(true);
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
