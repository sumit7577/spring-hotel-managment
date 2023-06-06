package com.hotel.jorvik.security.implementation;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.TokenType.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${site.domain}")
    private String domain;
    private final EmailSender emailSender;

    @Override
    public void sendConfirmationEmail(User user) {
        String confirmationToken = jwtService.generateConfirmationToken(user);
        String confirmEmailLink = domain + "/api/v1/auth/email-confirmation/" + confirmationToken;
        emailSender.sendEmail(
                user.getEmail(),
                "Confirm your email",
                "Please, confirm your email by clicking on the link: " + confirmEmailLink);
        jwtService.saveUserToken(user, confirmationToken, ETokenType.EMAIL_CONFIRMATION);
    }

    @Override
    public void confirmEmail(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(token, user) && jwtService.isEmailToken(token)) {
                jwtService.revokeAllUserTokens(user, ETokenType.EMAIL_CONFIRMATION);
                user.setVerified(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void sendResetPasswordEmail(User user) {
        String resetPasswordToken = jwtService.generatePasswordResetToken(user);
        String resetPasswordLink = domain + "/api/v1/user/password-reset-confirm/" + resetPasswordToken;
        emailSender.sendEmail(
                user.getEmail(),
                "Reset your password",
                "Please, reset your password by clicking on the link: " + resetPasswordLink);
        jwtService.saveUserToken(user, resetPasswordToken, ETokenType.RESET_PASSWORD);
    }
}
