package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    private final SecurityTools tools;

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
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.map(UserDTO::new).get();
    }

    @Override
    public void updatePassword(PasswordChangeRequest passwordChangeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!tools.isValidPassword(passwordChangeRequest.getNewPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }
        // Handle exception if password is not correct
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        passwordChangeRequest.getPassword()
                )
        );
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        repository.save(user.get());
    }

    @Override
    public void updateEmail(EmailChangeRequest emailChangeRequest) {
        repository.findByEmail(emailChangeRequest.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setEmail(emailChangeRequest.getEmail());
        user.get().setConfirmed(false);
        emailService.sendConfirmationEmail(user.get());
        repository.save(user.get());
    }

    @Override
    public void resentEmailVerification() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        emailService.sendConfirmationEmail(user.get());
    }

    @Override
    public void updatePhone(PhoneChangeRequest phoneChangeRequest) {
        if (!tools.isValidPhone(phoneChangeRequest.getPhone())) {
            throw new IllegalArgumentException("Phone is not valid");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setPhone(phoneChangeRequest.getPhone());
        repository.save(user.get());
    }

    @Override
    public void updateDiscount(int id, DiscountChangeRequest discountChangeRequest) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setDiscount(discountChangeRequest.getDiscount());
        repository.save(user.get());
    }
}
