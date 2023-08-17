package com.hotel.jorvik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class JorvikApplication {

    public static void main(String[] args) {
        SpringApplication.run(JorvikApplication.class, args);
    }

}
