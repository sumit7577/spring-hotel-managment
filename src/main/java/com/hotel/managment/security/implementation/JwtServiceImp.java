package com.hotel.managment.security.implementation;

import com.hotel.managment.models.Token;
import com.hotel.managment.models.TokenType;
import com.hotel.managment.models.TokenType.TokenTypeEnum;
import com.hotel.managment.models.User;
import com.hotel.managment.repositories.TokenRepository;
import com.hotel.managment.repositories.TokenTypeRepository;
import com.hotel.managment.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Implementation for managing JSON Web Token (JWT) related services in the application.
 *
 * <p>This service provides methods for generating, validating, and managing JWTs for various
 * purposes such as user authentication, email confirmation, and password reset. It includes
 * functionalities for token generation, extraction of information from tokens, and checking token
 * validity and type.
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImp implements JwtService {

  private final TokenRepository tokenRepository;
  private final TokenTypeRepository tokenTypeRepository;

  @Value("${jwt.secret}")
  private String SECRET_KEY;
  private static final long EXPIRATION_TIME_LOGIN = 86400000 * 10; // ms * d
  private static final long EXPIRATION_TIME_CONFIRM = 86400000 * 3; // ms * d
  private static final long EXPIRATION_TIME_PASSWORD_RESET = 86400000 / 2; // ms * d

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims(extractClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_LOGIN))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String generateConfirmationToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_CONFIRM))
        .claim("token_type", "email_confirmation")
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String generatePasswordResetToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_PASSWORD_RESET))
        .claim("token_type", "password_reset")
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public void revokeAllUserTokens(User user, TokenTypeEnum tokenType) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
    if (validUserTokens.isEmpty()) {
      return;
    }
    validUserTokens.forEach(
        token -> {
          if (token.getTokenType().getType().equals(tokenType)) {
            tokenRepository.delete(token);
          }
        });
    // tokenRepository.saveAll(validUserTokens);
  }

  @Override
  public boolean isTokenValid(String jwt, UserDetails userDetails) {
    final String username = extractUsername(jwt);
    boolean tokenDatabaseCheck = tokenRepository.findByToken(jwt).isPresent();
    boolean isValid = (username.equals(userDetails.getUsername()));
    return tokenDatabaseCheck && isValid;
  }

  @Override
  public void saveUserToken(User user, String jwtToken, TokenTypeEnum tokenType) {
    TokenType type =
        tokenTypeRepository
            .findByType(tokenType)
            .orElseThrow(() -> new RuntimeException("Token type not found"));
    Token token = Token.builder().user(user).token(jwtToken).tokenType(type).build();
    tokenRepository.save(token);
  }

  @Override
  public boolean isEmailToken(String jwt) {
    Claims claims = extractAllClaims(jwt);
    return claims.get("token_type", String.class).equals("email_confirmation");
  }

  @Override
  public boolean isPasswordToken(String jwt) {
    Claims claims = extractAllClaims(jwt);
    return claims.get("token_type", String.class).equals("password_reset");
  }

  @Override
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
