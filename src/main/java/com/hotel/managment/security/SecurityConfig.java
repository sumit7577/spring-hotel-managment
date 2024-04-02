package com.hotel.managment.security;

import com.hotel.managment.repositories.UserRepository;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration class for the application.
 *
 * <p>This class configures beans related to application security, including user details service,
 * authentication provider, password encoder, and SendGrid for email services. It sets up the
 * authentication manager and enables global method security.
 */
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
  private final UserRepository repository;

  @Value("${sendgrid.api}")
  private String sendGridApiKey;

  /**
   * Configures the UserDetailsService bean to load user details based on the provided username.
   *
   * @return An instance of UserDetailsService.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username ->
        repository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Configures the AuthenticationProvider bean to use the UserDetailsService and password encoder.
   *
   * @return An instance of AuthenticationProvider.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SendGrid sendGrid() {
    return new SendGrid(sendGridApiKey);
  }
}
