package com.hotel.jorvik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Jorvik Hotel application.
 * <p>
 * This class serves as the main class for a Spring Boot application, enabling
 * auto-configuration and scheduling capabilities.
 * </p>
 */
@SpringBootApplication()
@EnableScheduling
public class JorvikApplication {

  public static void main(String[] args) {
    SpringApplication.run(JorvikApplication.class, args);
  }
}
