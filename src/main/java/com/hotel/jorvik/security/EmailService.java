package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;

/**
 * Interface for managing email communications in the application.
 *
 * <p>This interface provides methods for sending various types of emails to users, including
 * confirmation emails for account creation, email verification, and password reset instructions. It
 * handles the email sending process for different user-related actions.
 */
public interface EmailService {
  /**
   * Sends a confirmation email to the user for account creation or other confirmation purposes.
   *
   * @param user The user to whom the confirmation email will be sent.
   */
  void sendConfirmationEmail(User user);

  /**
   * Confirms a user's email based on a provided token.
   *
   * @param token The token used to verify the user's email.
   */
  void confirmEmail(String token);

  /**
   * Sends a password reset email to the user.
   *
   * @param user The user who requested a password reset.
   */
  void sendResetPasswordEmail(User user);
}
