package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.DTO.*;
import com.hotel.jorvik.models.TokenType;
import com.hotel.jorvik.models.User;
import com.hotel.jorvik.repositories.UserRepository;
import com.hotel.jorvik.security.AuthenticationResponse;
import com.hotel.jorvik.security.EmailService;
import com.hotel.jorvik.security.JwtService;
import com.hotel.jorvik.security.SecurityTools;
import com.hotel.jorvik.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final SecurityTools tools;
    private final JwtService jwtService;

    @Override
    public List<UserDTO> getAll() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.map(UserDTO::new).get();
    }

    @Override
    public UserDTO getByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.map(UserDTO::new).get();
    }

    @Override
    public boolean checkEmailVerification() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new NoSuchElementException("User not found");
        }
        return user.get().getVerified() != null;
    }

    @Override
    @Transactional
    public void updatePassword(PasswordChangeRequest passwordChangeRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!tools.isValidPassword(passwordChangeRequest.getNewPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        passwordChangeRequest.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user.get());
    }

    @Override
    @Transactional
    public AuthenticationResponse updateEmail(EmailChangeRequest emailChangeRequest) {
        userRepository.findByEmail(emailChangeRequest.getEmail())
                .ifPresent(row -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        user.get().setEmail(emailChangeRequest.getEmail());
        user.get().setVerified(null);
        emailService.sendConfirmationEmail(user.get());
        userRepository.save(user.get());

        String jwtToken = jwtService.generateToken(user.get());
        jwtService.revokeAllUserTokens(user.get(), TokenType.ETokenType.ACCESS);
        jwtService.saveUserToken(user.get(), jwtToken, TokenType.ETokenType.ACCESS);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public void resentEmailVerification() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        emailService.sendConfirmationEmail(user.get());
    }

    @Override
    @Transactional
    public void updatePhone(PhoneChangeRequest phoneChangeRequest) {
        if (!tools.isValidPhone(phoneChangeRequest.getPhone())) {
            throw new IllegalArgumentException("Phone is not valid");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setPhone(phoneChangeRequest.getPhone());
        userRepository.save(user.get());
    }

    @Override
    @Transactional
    public void updateDiscount(int id, DiscountChangeRequest discountChangeRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }
        user.get().setDiscount(discountChangeRequest.getDiscount());
        userRepository.save(user.get());
    }
}
