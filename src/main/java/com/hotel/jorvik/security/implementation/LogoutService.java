package com.hotel.jorvik.security.implementation;

import com.hotel.jorvik.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/** Service to handle logout functionality. */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  /**
   * Processes a logout request by invalidating the JWT token.
   *
   * @param request The HTTP request containing the JWT token.
   * @param response The HTTP response.
   * @param authentication The current authentication object.
   */
  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    jwt = authHeader.substring(7);
    tokenRepository.findByToken(jwt).ifPresent(tokenRepository::delete);
  }
}
