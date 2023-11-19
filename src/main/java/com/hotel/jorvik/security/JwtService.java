package com.hotel.jorvik.security;

import com.hotel.jorvik.models.TokenType.TokenTypeEnum;
import com.hotel.jorvik.models.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface for managing JSON Web Token (JWT) related services in the application.
 *
 * <p>This interface provides methods for generating, validating, and managing JWTs for various
 * purposes such as user authentication, email confirmation, and password reset. It includes
 * functionalities for token generation, extraction of information from tokens, and checking token
 * validity and type.
 */
public interface JwtService {
  String extractUsername(String token);

  String generateToken(UserDetails userDetails);

  String generateConfirmationToken(UserDetails userDetails);

  void revokeAllUserTokens(User user, TokenTypeEnum tokenType);

  boolean isTokenValid(String jwt, UserDetails userDetails);

  boolean isEmailToken(String jwt);

  boolean isPasswordToken(String jwt);

  boolean isTokenExpired(String token);

  void saveUserToken(User user, String jwtToken, TokenTypeEnum tokenType);

  String generatePasswordResetToken(UserDetails userDetails);
}
