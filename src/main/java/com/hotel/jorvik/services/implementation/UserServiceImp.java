package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.models.enums.ETokenType;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import com.hotel.jorvik.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Override
    public Iterable<UserDTO> getAll() {
        Iterable<User> users = repository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(int id) {
        Optional<User> user = repository.findById(id);
        return user.map(UserDTO::new).orElse(null);
    }

    @Override
    public boolean updatePassword(PasswordChangeRequest passwordChangeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            passwordChangeRequest.getPassword()
                    )
            );
        }catch (Exception e){
            return false;
        }
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        repository.save(user.get());
        return true;
    }

    @Override
    public boolean updateEmail(EmailChangeRequest emailChangeRequest) {
        repository.findByEmail(emailChangeRequest.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setEmail(emailChangeRequest.getEmail());
        user.get().setConfirmed(false);
        emailService.sendConfirmationEmail(user.get());
        repository.save(user.get());
        return true;
    }

    @Override
    public boolean resentEmailVerification() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        emailService.sendConfirmationEmail(user.get());
        return true;
    }

    @Override
    public boolean updatePhone(PhoneChangeRequest phoneChangeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setPhone(phoneChangeRequest.getPhone());
        repository.save(user.get());
        return true;
    }

    @Override
    public boolean updateDiscount(int id, DiscountChangeRequest discountChangeRequest) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            return false;
        }
        user.get().setDiscount(discountChangeRequest.getDiscount());
        repository.save(user.get());
        return true;
    }
}
