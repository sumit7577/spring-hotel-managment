package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${site.domain}")
    private String domain;
    private final EmailSender emailSender;

    public void sendConfirmationEmail(User user) {
        String confirmationToken = jwtService.generateConfirmationToken(user);
        String confirmEmailLink = domain + "/api/auth/email-confirmation/" + confirmationToken;
        emailSender.sendEmail(
                user.getEmail(),
                "Confirm your email",
                "Please, confirm your email by clicking on the link: " + confirmEmailLink);
        jwtService.saveUserToken(user, confirmationToken, ETokenType.CONFIRMATION);
    }

    public void confirmEmail(String token) {
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(token, user) && jwtService.isEmailToken(token)) {
                jwtService.revokeAllUserTokens(user, ETokenType.CONFIRMATION);
                user.setConfirmed(true);
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
