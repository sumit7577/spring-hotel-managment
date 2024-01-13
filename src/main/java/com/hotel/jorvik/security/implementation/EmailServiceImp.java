package com.hotel.jorvik.security.implementation;

import com.hotel.jorvik.models.TokenType.TokenTypeEnum;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation for managing email communications in the application.
 *
 * <p>This service provides methods for sending various types of emails to users, including
 * confirmation emails for account creation, email verification, and password reset instructions. It
 * handles the email sending process for different user-related actions.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Value("${frontend.url}")
  private String frontEndDomain;

  private final EmailSender emailSender;

  /**
   * Sends a confirmation email to the user for account creation or other confirmation purposes.
   *
   * @param user The user to whom the confirmation email will be sent.
   */
  @Transactional
  @Override
  public void sendConfirmationEmail(User user) {
    String confirmationToken = jwtService.generateConfirmationToken(user);
    String confirmEmailLink =
        frontEndDomain + "/email-confirmation?emailToken=" + confirmationToken;
    Map<String, String> substitutions = new HashMap<>();
    substitutions.put("firstName", user.getFirstName());
    substitutions.put("lastName", user.getLastName());
    substitutions.put("confirmEmailLink", confirmEmailLink);
    emailSender.sendEmail(
        user.getEmail(),
        "d-bbe64d97ea05443993e1e44c0869bfa5",
        substitutions
    );
    jwtService.revokeAllUserTokens(user, TokenTypeEnum.EMAIL_CONFIRMATION);
    jwtService.saveUserToken(user, confirmationToken, TokenTypeEnum.EMAIL_CONFIRMATION);
  }

  /**
   * Confirms a user's email based on a provided token.
   *
   * @param token The token used to verify the user's email.
   */
  @Transactional
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
      if (!jwtService.isTokenValid(token, user) || !jwtService.isEmailToken(token)) {
        throw new IllegalArgumentException("Token is invalid");
      } else if (jwtService.isTokenExpired(token)) {
        throw new IllegalArgumentException("Token is expired");
      } else {
        jwtService.revokeAllUserTokens(user, TokenTypeEnum.EMAIL_CONFIRMATION);
        user.setVerified(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
      }
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

  /**
   * Sends a password reset email to the user.
   *
   * @param user The user who requested a password reset.
   */
  @Transactional
  public void sendResetPasswordEmail(User user) {
    String resetPasswordToken = jwtService.generatePasswordResetToken(user);
    String resetPasswordLink = frontEndDomain + "/login?resetToken=" + resetPasswordToken;
    Map<String, String> substitutions = new HashMap<>();
    substitutions.put("firstName", user.getFirstName());
    substitutions.put("lastName", user.getLastName());
    substitutions.put("resetPasswordLink", resetPasswordLink);
    emailSender.sendEmail(
        user.getEmail(),
        "d-4878326906ad4afa8bbbceb371352962",
        substitutions
    );
    jwtService.revokeAllUserTokens(user, TokenTypeEnum.RESET_PASSWORD);
    jwtService.saveUserToken(user, resetPasswordToken, TokenTypeEnum.RESET_PASSWORD);
  }
}
