package com.hotel.managment.security;

import com.hotel.managment.models.User;
import com.hotel.managment.repositories.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Utility class for security-related operations. */
@Component
@RequiredArgsConstructor
public class SecurityTools {

  private final UserRepository userRepository;

  /**
   * Checks if the provided phone number is valid.
   *
   * @param phone The phone number to validate.
   * @return true if the phone number is valid, false otherwise.
   */
  public boolean isValidPhone(String phone) {
    return phone.matches("^\\+?[0-9\\s]*$");
  }

  /**
   * Validates the strength of a password.
   *
   * @param password The password to validate.
   * @return true if the password meets the strength criteria, false otherwise.
   */
  public boolean isValidPassword(String password) {
    if (password == null || password.length() < 8) {
      return false;
    }
    if (!password.matches(".*[A-Z].*")) {
      return false;
    }
    if (!password.matches(".*[a-z].*")) {
      return false;
    }
    if (!password.matches(".*[0-9].*")) {
      return false;
    }
    return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*");
  }

  /**
   * Retrieves user data based on the authenticated user's email.
   *
   * @return The User object associated with the current authenticated user.
   * @throws NoSuchElementException if the user is not found.
   */
  public User retrieveUserData() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    return user.get();
  }
}
