package com.hotel.jorvik.security;

import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityTools {

    private final UserRepository userRepository;

    public boolean isValidPhone(String phone) {
        return phone.matches("^\\+?[0-9\\s]*$");
    }

    public boolean isValidPassword(String password) {
        if(password == null || password.length() < 8) {
            return false;
        }
        if(!password.matches(".*[A-Z].*")) {
            return false;
        }
        if(!password.matches(".*[a-z].*")) {
            return false;
        }
        if(!password.matches(".*[0-9].*")) {
            return false;
        }
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?].*");
    }

    public User retrieveUserData(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        return user.get();
    }
}
