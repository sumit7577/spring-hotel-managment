package com.hotel.managment.configuration;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class, {@code StripeConfig}, is responsible for initializing the Stripe API
 * with the secret key obtained from application properties. It sets the Stripe API key during
 * application startup.
 */
@Configuration
public class StripeConfig {

  @Value("${stripe.secretKey}")
  private String secretKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = this.secretKey;
  }
}
