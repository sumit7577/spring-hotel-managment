package com.hotel.managment;

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
public class HotelManagment {

  public static void main(String[] args) {
    SpringApplication.run(HotelManagment.class, args);
  }
}
