package com.hotel.jorvik.security.implementation;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.TokenType.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import jakarta.transaction.Transactional;
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
    @Value("${frontend.url}")
    private String frontEndDomain;
    private final EmailSender emailSender;

    @Transactional
    @Override
    public void sendConfirmationEmail(User user) {
        String confirmationToken = jwtService.generateConfirmationToken(user);
        String confirmEmailLink = frontEndDomain + "/email-confirmation?emailToken=" + confirmationToken;
        emailSender.sendEmail(
                user.getEmail(),
                "Confirm your email",
                "Please, confirm your email by clicking on the link: " + confirmEmailLink);
        jwtService.revokeAllUserTokens(user, ETokenType.EMAIL_CONFIRMATION);
        jwtService.saveUserToken(user, confirmationToken, ETokenType.EMAIL_CONFIRMATION);
    }

    @Transactional
    @Override
    public void confirmEmail(String token) {
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        System.out.println(111111111);
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        final String userEmail;
        userEmail = jwtService.extractUsername(token);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (!jwtService.isTokenValid(token, user) || !jwtService.isEmailToken(token)) {
                throw new IllegalArgumentException("Token is invalid");
            }
            else if (jwtService.isTokenExpired(token)) {
                throw new IllegalArgumentException("Token is expired");
            }
            else {
                jwtService.revokeAllUserTokens(user, ETokenType.EMAIL_CONFIRMATION);
                user.setVerified(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional
    public void sendResetPasswordEmail(User user) {
        String resetPasswordToken = jwtService.generatePasswordResetToken(user);
        String resetPasswordLink = frontEndDomain + "/login?resetToken=" + resetPasswordToken;
        emailSender.sendEmail(
                user.getEmail(),
                "Reset your password",
                "Please, reset your password by clicking on the link: " + resetPasswordLink);
        jwtService.revokeAllUserTokens(user, ETokenType.RESET_PASSWORD);
        jwtService.saveUserToken(user, resetPasswordToken, ETokenType.RESET_PASSWORD);
    }
}
